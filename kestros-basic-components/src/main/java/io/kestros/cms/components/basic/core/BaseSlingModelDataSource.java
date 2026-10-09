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
import io.kestros.cms.uiframeworks.api.exceptions.InvalidThemeException;
import io.kestros.cms.uiframeworks.api.exceptions.ThemeRetrievalException;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
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
  @Nonnull
  public Boolean isSynthetic() {
    return Boolean.FALSE;
  }

  /**
   * Resource this data source was adapted from.
   *
   * @return Resource this data source was adapted from.
   * @throws IllegalStateException when the model was adapted from neither a Resource nor a
   *     request.
   */
  @Nonnull
  public Resource getResource() {
    if (resource == null && slingHttpServletRequest != null) {
      return slingHttpServletRequest.getResource();
    }
    if (resource == null) {
      throw new IllegalStateException(
          "Unable to resolve Resource: model was adapted from neither a Resource nor a request.");
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
   * Page the component is being rendered on, or the page that contains it.
   *
   * @return Page the component is being rendered on, or null when neither can be resolved.
   */
  @JsonIgnore
  @Nullable
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
      } catch (NoValidAncestorException exception) {
        return null;
      }
    }
    return currentPage;
  }


  /**
   * ResourceResolver the containing Resource was resolved with.
   *
   * @return ResourceResolver the containing Resource was resolved with.
   */
  @Nonnull
  public ResourceResolver getResourceResolver() {
    return getResource().getResourceResolver();
  }

  /**
   * Path of the containing Resource.
   *
   * @return Path of the containing Resource.
   * @throws IllegalStateException when the Resource has no parent.
   */
  @Nonnull
  public String getParentPath() {
    Resource parent = getResource().getParent();
    if (parent == null) {
      throw new IllegalStateException(
          String.format("Resource %s has no parent.", getResource().getPath()));
    }
    return parent.getPath();
  }

  /**
   * Variations applied to the element.
   *
   * @return Variations applied to the element.
   */
  @Nonnull
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
    BaseComponent component = getResource().adaptTo(BaseComponent.class);
    if (component == null) {
      return new ArrayList<>();
    }
    return component.getAppliedVariations();
  }

  /**
   * Layout configured on the resource, defaulting to "default".
   *
   * @return Layout configured on the resource, defaulting to "default".
   */
  @Nonnull
  public String getLayout() {
    return getResource().getValueMap().get("layout", "default");
  }


  @Nullable
  @Override
  public String getForcedResourceName() {
    return getResource().getName();
  }

  /**
   * UiFramework the current or containing page is themed with.
   *
   * @return UiFramework the current or containing page is themed with.
   * @throws IllegalStateException when no current or containing page can be resolved.
   * @throws ResourceNotFoundException when the page's Theme cannot be retrieved.
   * @throws InvalidThemeException when the page's Theme is not valid.
   * @throws UiFrameworkRetrievalException when the Theme's UiFramework cannot be retrieved.
   * @throws ThemeRetrievalException when the page's Theme cannot be retrieved.
   */
  @Nonnull
  public UiFramework getUiFramework() throws ResourceNotFoundException, InvalidThemeException,
      UiFrameworkRetrievalException, ThemeRetrievalException {
    BaseContentPage page = getCurrentOrContainingPage();
    if (page == null) {
      throw new IllegalStateException(
          "Unable to resolve a current or containing page to read the UiFramework from.");
    }
    return page.getTheme().getUiFramework();
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
