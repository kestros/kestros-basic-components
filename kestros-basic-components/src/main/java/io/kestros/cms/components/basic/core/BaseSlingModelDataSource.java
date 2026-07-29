package io.kestros.cms.components.basic.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentElementRenderingException;
import io.kestros.cms.componenttypes.api.exceptions.ComponentVariationRetrievalException;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
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

/**
 * Base for component elements adapted from an authored resource or its request. Resolves the
 * containing page, the UI framework in force and the variations applied to the element.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public abstract class BaseSlingModelDataSource extends BaseComponentElement {

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
   * Id.
   *
   * @return Id.
   */
  @Nullable
  public String getId() {
    return getResource().getValueMap().get("id", String.class);
  }

  /**
   * Synthetic.
   *
   * @return Synthetic.
   */
  @Nonnull
  public Boolean isSynthetic() {
    return Boolean.FALSE;
  }

  /**
   * Resource.
   *
   * @return Resource.
   */
  @Nonnull
  public Resource getResource() {
    if (resource == null && slingHttpServletRequest != null) {
      return slingHttpServletRequest.getResource();
    }
    return resource;
  }

  /**
   * Request.
   *
   * @return Request.
   */
  @Nullable
  public SlingHttpServletRequest getRequest() {
    return slingHttpServletRequest;
  }

  /**
   * The page this element is rendering on: the current page when adapted from a request, or the
   * page containing the element's resource otherwise.
   *
   * @return The page this element is rendering on.
   */
  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed exception so the failure stays identifiable, per"
          + " the ruling on DataSourceComponent.")
  @JsonIgnore
  @Nonnull
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
   * Resource resolver.
   *
   * @return Resource resolver.
   */
  @Nonnull
  public ResourceResolver getResourceResolver() {
    return getResource().getResourceResolver();
  }

  /**
   * Parent path.
   *
   * @return Parent path.
   */
  @Nonnull
  public String getParentPath() {
    return getResource().getParent().getPath();
  }

  /**
   * Variations.
   *
   * @return Variations.
   */
  @Nonnull
  public List<ComponentVariation> getVariations() {
    // TODO verify this.
    List<Map<String, Object>> variationsMapList = getResource().getValueMap()
        .get("variations", new ArrayList<>());
    if (!variationsMapList.isEmpty()) {
      final List<Map<String, Object>> sourceVariations = variationsMapList;
      final List<ComponentVariation> variations = new ArrayList<>(sourceVariations.size());
      for (Map<String, Object> variationMap : sourceVariations) {
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
   * Layout.
   *
   * @return Layout.
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
   * UI framework in force for this element, resolved from the containing page's theme.
   *
   * @return UI framework in force for this element.
   * @throws ComponentElementRenderingException If the containing page, its theme or its UI
   *     framework cannot be resolved.
   */
  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_HAS_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The"
          + " checked cause is wrapped in a typed exception, per the ruling on"
          + " DataSourceComponent.")
  @Nonnull
  public UiFramework getUiFramework() {
    try {
      if (slingHttpServletRequest != null) {
        return slingHttpServletRequest.adaptTo(ComponentRequestContext.class).getCurrentPage()
            .getTheme().getUiFramework();
      } else {
        return getResource().adaptTo(BaseComponent.class).getContainingPage().getTheme()
            .getUiFramework();
      }
    } catch (ModelAdaptionException e) {
      throw new ComponentElementRenderingException(String.format(
          "Unable to resolve the UI framework for %s.", getPath()), e);
    }
  }

  /**
   * Component variation retrieval service.
   *
   * @return Component variation retrieval service.
   */
  @Nonnull
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  /**
   * Component ui framework view retrieval service.
   *
   * @return Component ui framework view retrieval service.
   */
  @Nonnull
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }

}