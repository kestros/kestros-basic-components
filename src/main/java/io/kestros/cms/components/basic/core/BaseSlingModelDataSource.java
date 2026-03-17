package io.kestros.cms.components.basic.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.componenttypes.api.exceptions.ComponentViewRetrievalException;
import io.kestros.cms.componenttypes.api.models.ComponentUiFrameworkView;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.commons.commonutils.jcr.JcrPropertyUtils;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
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
    // Read the applied variation names/paths from the resource property
    List<String> appliedVariationNames = new ArrayList<>();
    Object propertyValue = getResource().getValueMap().get("variations");
    if (propertyValue instanceof List && !((List<?>) propertyValue).isEmpty()
        && ((List<?>) propertyValue).get(0) instanceof Map) {
      // dialog-saved format: list of maps with "path" key
      List<Map<String, Object>> variationMaps = (List<Map<String, Object>>) propertyValue;
      for (Map<String, Object> variationMap : variationMaps) {
        String path = (String) variationMap.get("path");
        if (path != null) {
          appliedVariationNames.add(path);
        }
      }
    } else {
      appliedVariationNames = JcrPropertyUtils.getStringListOrEmptyList(
          getResource(), "variations");
    }

    if (appliedVariationNames.isEmpty() && !getResource().getValueMap().containsKey("variations")) {
      return new ArrayList<>();
    }

    // Resolve variations by fetching all available variations for this component's view
    // and matching by name or path — same pattern as getElementVariations()
    final List<ComponentVariation> appliedVariations = new ArrayList<>();
    try {
      BaseComponent component = getResource().adaptTo(BaseComponent.class);
      ComponentUiFrameworkView uiFrameworkView =
          getComponentUiFrameworkViewRetrievalService().getResolvedComponentUiFrameworkView(
              getComponentResourceType(), getUiFramework(),
              component != null ? component.getResourceResolver() : getResourceResolver());
      List<ComponentVariation> allVariations =
          getComponentVariationRetrievalService().getComponentVariations(uiFrameworkView);
      for (String appliedName : appliedVariationNames) {
        for (ComponentVariation variation : allVariations) {
          if (variation.getPath().equals(appliedName)
              || variation.getName().equals(appliedName)) {
            appliedVariations.add(variation);
            break;
          }
        }
      }
    } catch (ModelAdaptionException | ComponentViewRetrievalException e) {
      // ignore — return what we have
    }
    return appliedVariations;
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