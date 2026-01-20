package io.kestros.cms.components.basic.core.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.SyntheticResourceWrapper;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

public abstract class BaseSyntheticResource implements KestrosBasicComponentElement {
  private final ResourceResolver resourceResolver;
  private final String parentPath;
  private final UiFramework uiFramework;
  private final List<ComponentVariation> componentVariations;
  private final String layout;
  private final String id;
  private Resource syntheticResource;
  private Resource resource;
  private String resourceName;

  public BaseSyntheticResource(@Nonnull ResourceResolver resourceResolver,
          @Nonnull UiFramework uiFramework,
          @Nonnull String parentPath, @Nonnull List<ComponentVariation> componentVariations,
          @Nonnull String layout, @Nullable String id, @Nullable String forcedResourceName) throws
          ComponentConfigurationException {
    this.resourceResolver = resourceResolver;
    this.parentPath = parentPath;
    this.componentVariations = componentVariations;
    this.layout = layout;
    this.uiFramework = uiFramework;
    this.id = id;
    this.resourceName = forcedResourceName;
    if (resourceResolver == null || this.parentPath == null || this.componentVariations == null
            || this.layout == null || this.uiFramework == null) {
      // this is not needed, but is included so that the extending classes are required to throw
      // the exception.
      throw new ComponentConfigurationException("Missing required property");
    }
  }

  @Override
  public Resource toSyntheticResource(@Nonnull ResourceResolver resourceResolver,
          @Nonnull String parentPath) {
    if (syntheticResource == null) {
      ResourceMetadata resourceMetadata = new ResourceMetadata();
      String name = "child-" + java.util.UUID.randomUUID();
      if (this.getForcedResourceName() != null && !this.getForcedResourceName().isEmpty()) {
        name = this.getForcedResourceName();
      }
      String path = parentPath + "/" + name;
      if (!path.startsWith("/synthetics")) {
        path = "/synthetics" + path;
      }
      resourceMetadata.setResolutionPath(path);
      resourceMetadata.setModificationTime(System.currentTimeMillis());
      Map<String, String> parameters = new HashMap<>();
      resourceMetadata.setParameterMap(parameters);
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> props = objectMapper.convertValue(this, Map.class);
      props.put("sling:resourceType", getComponentResourceType());
      props.put("jcr:primaryType", "nt:unstructured");
      syntheticResource = new SyntheticResource(resourceResolver, resourceMetadata,
              getComponentResourceType()) {
        private final ValueMap valueMap = new ValueMapDecorator(props);

        @Override
        public ValueMap getValueMap() {
          return valueMap;
        }
      };
      if (this instanceof KestrosContainerElement) {
        KestrosContainerElement container = (KestrosContainerElement) this;
        Map<String, Resource> childResources = new HashMap<>();
        for (KestrosBasicComponentElement child : container.getChildElements()) {
          Resource childSyntheticResource = child.toSyntheticResource(resourceResolver,
                  syntheticResource.getPath());
          childResources.put(childSyntheticResource.getName(), childSyntheticResource);
        }
        syntheticResource = new SyntheticResourceWrapper(syntheticResource, childResources);

      }
    }

    return syntheticResource;
  }

  @Nonnull
  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public ResourceResolver getResourceResolver() {
    return resourceResolver;
  }

  @Override
  public String getParentPath() {
    return parentPath;
  }

  @Override
  public Resource getResource() {
    if (syntheticResource == null) {
      syntheticResource = toSyntheticResource(resourceResolver, parentPath);
    }
    return syntheticResource;
  }

  @Nullable
  @Override
  public String getForcedResourceName() {
    return resourceName;
  }

  @Override
  public List<ComponentVariation> getVariations() {
    return new ArrayList<>(componentVariations);
  }

  @Override
  public String getLayout() {
    return layout;
  }

  public UiFramework getUiFramework() {
    return uiFramework;
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return null;
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return null;
  }
}