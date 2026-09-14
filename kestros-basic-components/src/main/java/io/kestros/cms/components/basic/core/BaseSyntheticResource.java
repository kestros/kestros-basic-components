package io.kestros.cms.components.basic.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public abstract class BaseSyntheticResource extends BaseComponentElement {
  private final String parentPath;
  private final UiFramework uiFramework;
  private final List<ComponentVariation> componentVariations;
  private final String layout;
  private final String id;
  private Resource syntheticResource;
  private String resourceName;
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  private BaseSlingModelDataSource dataSource;

  @SuppressFBWarnings(value = {"MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR",
      "OPM_OVERLY_PERMISSIVE_METHOD"},
      justification = "getComponentResourceType is the subclass's declaration of which"
          + " component it is, and the variations lookup needs it while the object is still"
          + " being built. Every implementation returns a constant.")
  public BaseSyntheticResource(
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    this.dataSource = dataSource;
    final ResourceResolver resourceResolver = dataSource.getResourceResolver();
    this.parentPath = dataSource.getResource().getPath();
    this.uiFramework = dataSource.getUiFramework();
    this.componentVariations = dataSource.getElementVariations(resourcePrefix + "Variations",
        getComponentResourceType());
    this.layout = dataSource.getLayout(resourcePrefix);
    this.id = null;
    this.resourceName = forcedResourceName;
    if (resourceResolver == null || this.parentPath == null || this.componentVariations == null
        || this.layout == null || this.uiFramework == null) {
      // this is not needed, but is included so that the extending classes are required to throw
      // the exception.
      throw new ComponentConfigurationException(String.format(
          "Unable to build a synthetic resource under %s: one of the resource resolver, parent path,%n"
          + " variations, layout or UI framework was not supplied.",
          String.valueOf(parentPath)));
    }
    this.componentVariationRetrievalService = dataSource.getComponentVariationRetrievalService();
    this.componentUiFrameworkViewRetrievalService =
        dataSource.getComponentUiFrameworkViewRetrievalService();
  }

  @Nonnull
  @Override
  public String getId() {
    return this.id;
  }

  @Override
  @Nonnull
  public ResourceResolver getResourceResolver() {
    return dataSource.getResourceResolver();
  }

  @Override
  @Nonnull
  public String getParentPath() {
    return parentPath;
  }

  @Override
  @Nonnull
  public Resource getResource() {
    if (syntheticResource == null) {
      syntheticResource = toSyntheticResource(getResourceResolver(), parentPath);
    }
    return syntheticResource;
  }

  @Nullable
  @Override
  public SlingHttpServletRequest getRequest() {
    return dataSource.getRequest();
  }

  @Nullable
  @Override
  public String getForcedResourceName() {
    return resourceName;
  }

  @Override
  @Nonnull
  public List<ComponentVariation> getVariations() {
    return new ArrayList<>(componentVariations);
  }

  @Override
  @Nonnull
  public String getLayout() {
    return layout;
  }

  @Nonnull
  public UiFramework getUiFramework() {
    return uiFramework;
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }
}