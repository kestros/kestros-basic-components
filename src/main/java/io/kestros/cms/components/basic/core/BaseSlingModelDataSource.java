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

  @Nullable
  public String getId() {
    return getResource().getValueMap().get("id", String.class);
  }

  public Boolean isSynthetic() {
    return false;
  }

  public Resource getResource() {
    if (resource == null && slingHttpServletRequest != null) {
      return slingHttpServletRequest.getResource();
    }
    return resource;
  }

  @Nullable
  public SlingHttpServletRequest getRequest() {
    return slingHttpServletRequest;
  }

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


  public ResourceResolver getResourceResolver() {
    return getResource().getResourceResolver();
  }

  public String getParentPath() {
    return getResource().getParent().getPath();
  }

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

  public String getLayout() {
    return getResource().getValueMap().get("layout", "default");
  }


  @Nullable
  @Override
  public String getForcedResourceName() {
    return getResource().getName();
  }

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

  @Nonnull
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  @Nonnull
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }

}