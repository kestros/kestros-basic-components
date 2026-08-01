package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
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

public abstract class BaseSyntheticResource extends BaseComponentElement
    implements KestrosBasicComponentElement {
  private final ResourceResolver resourceResolver;
  private final String parentPath;
  private final UiFramework uiFramework;
  private final List<ComponentVariation> componentVariations;
  private final String layout;
  private final String id;
  private Resource syntheticResource;
  private Resource resource;
  private String resourceName;
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  private BaseSlingModelDataSource dataSource;

  public BaseSyntheticResource(
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    this.dataSource = dataSource;
    this.resourceResolver = dataSource.getResourceResolver();
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
      throw new ComponentConfigurationException("Missing required property");
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
    return resourceResolver;
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
      syntheticResource = toSyntheticResource(resourceResolver, parentPath);
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