/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.componenttypes.api.exceptions.ComponentVariationRetrievalException;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;

/**
 * Baseline Sling Model for datasource components, adaptable from either a request or a resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public abstract class BaseSlingModelDataSource
    extends BaseComponentElement implements KestrosBasicComponentElement {

  @Self
  @Optional
  private SlingHttpServletRequest slingHttpServletRequest;

  @Self
  @Optional
  private Resource resource;

  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;

  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  private Resource syntheticResource;

  /**
   * Unique id configured on the resource, if one was set.
   *
   * @return Unique id configured on the resource, if one was set.
   */
  @Nullable
  public String getId() {
    return getResource().getValueMap().get("id", String.class);
  }

  /**
   * Whether the element is synthetic rather than backed by a stored resource.
   *
   * @return Whether the element is synthetic rather than backed by a stored resource.
   */
  public Boolean isSynthetic() {
    return false;
  }

  /**
   * Resource the model was adapted from, falling back to the request's resource.
   *
   * @return Resource the model was adapted from.
   */
  public Resource getResource() {
    if (resource == null && slingHttpServletRequest != null) {
      return slingHttpServletRequest.getResource();
    }
    return resource;
  }

  /**
   * Request the model was adapted from, if it was adapted from one.
   *
   * @return Request the model was adapted from, if it was adapted from one.
   */
  @Nullable
  public SlingHttpServletRequest getRequest() {
    return slingHttpServletRequest;
  }

  /**
   * Page currently being rendered, falling back to the page containing this resource.
   *
   * @return Page currently being rendered, falling back to the page containing this resource.
   */
  @JsonIgnore
  public BaseContentPage getCurrentOrContainingPage() {
    BaseContentPage currentPage = null;
    if (slingHttpServletRequest != null) {
      ComponentRequestContext componentRequestContext =
          slingHttpServletRequest.adaptTo(ComponentRequestContext.class);
      if (componentRequestContext != null) {
        currentPage = componentRequestContext.getCurrentPage();
      }
    }
    if (currentPage == null) {
      try {
        BaseComponent component = getResource().adaptTo(BaseComponent.class);
        if (component != null) {
          currentPage = component.getContainingPage();
        }
      } catch (NoValidAncestorException e) {
        throw new RuntimeException(e);
      }
    }
    return currentPage;
  }


  /**
   * ResourceResolver the model reads through.
   *
   * @return ResourceResolver the model reads through.
   */
  public ResourceResolver getResourceResolver() {
    return getResource().getResourceResolver();
  }

  /**
   * Path of the resource that contains this one.
   *
   * @return Path of the resource that contains this one.
   */
  public String getParentPath() {
    return getResource().getParent().getPath();
  }

  /**
   * Variations applied to the component, read from the resource where they were configured
   * inline and from the adapted component otherwise.
   *
   * @return Variations applied to the component.
   */
  public List<ComponentVariation> getVariations() {
    // TODO verify this.
    List<Map<String, Object>> variationsMapList = getResource().getValueMap()
        .get("variations", new ArrayList<>());
    if (!variationsMapList.isEmpty()) {
      List<ComponentVariation> variations = new ArrayList<>();
      for (Map<String, Object> variationMap : variationsMapList) {
        String path = (String) variationMap.get("path");
        Resource variationResource = getResourceResolver().getResource(path);
        if (variationResource == null) {
          continue;
        }
        try {
          ComponentVariation variation
              = getComponentVariationRetrievalService().getComponentVariation(path,
              getResourceResolver());
          variations.add(variation);
        } catch (ComponentVariationRetrievalException e) {
          continue;
        }

      }
      return variations;
    }
    return getResource().adaptTo(BaseComponent.class).getAppliedVariations();
  }

  /**
   * Layout the component renders with, defaulting to "default".
   *
   * @return Layout the component renders with.
   */
  public String getLayout() {
    return getResource().getValueMap().get("layout", "default");
  }


  @Nullable
  @Override
  public String getForcedResourceName() {
    return getResource().getName();
  }

  /**
   * UiFramework the component renders against, taken from the current page when the model was
   * adapted from a request and from the containing page otherwise.
   *
   * @return UiFramework the component renders against.
   * @throws RuntimeException Theme or UiFramework could not be resolved.
   */
  public UiFramework getUiFramework() {
    try {
      if (slingHttpServletRequest != null) {
        return slingHttpServletRequest.adaptTo(ComponentRequestContext.class).getCurrentPage()
            .getTheme().getUiFramework();
      } else {
        return getResource().adaptTo(BaseComponent.class).getContainingPage().getTheme()
            .getUiFramework();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Service the component looks its variations up through.
   *
   * @return Service the component looks its variations up through.
   */
  @Nonnull
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  /**
   * Service the component looks its UiFramework views up through.
   *
   * @return Service the component looks its UiFramework views up through.
   */
  @Nonnull
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }

}
