package io.kestros.cms.components.basic.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public abstract class BaseDataSourceComponent<T extends KestrosBasicComponentElement>
    extends BaseSlingModelDataSource implements KestrosBasicComponentElement {

  @Self
  @Optional
  private SlingHttpServletRequest slingRequest;

  private Resource syntheticResource;
  private T componentData;

  @Override
  public Boolean isSynthetic() {
    return true;
  }

  /**
   * Resolves the active datasource for this component by reading the configured datasource
   * variant's classPath from the component's datasource child resource and adapting the request
   * to that class.
   */
  @SuppressWarnings("unchecked")
  protected T getComponentData() {
    if (componentData != null) {
      return componentData;
    }
    Resource resource = getResource();
    if (resource == null) {
      return null;
    }
    ResourceResolver resolver = slingRequest != null
        ? slingRequest.getResourceResolver() : resource.getResourceResolver();
    // Find the datasource variant node — look at the child named by the "kes:datasource" property,
    // or fall back to "default"
    String datasourceVariant = resource.getValueMap().get("kes:datasource", "default");
    Resource componentTypeResource = resolver
        .getResource(getComponentResourceType());
    if (componentTypeResource == null) {
      return null;
    }
    Resource datasourcesNode = componentTypeResource.getChild("datasources");
    if (datasourcesNode == null) {
      return null;
    }
    Resource datasourceNode = datasourcesNode.getChild(datasourceVariant);
    if (datasourceNode == null) {
      datasourceNode = datasourcesNode.getChild("default");
    }
    if (datasourceNode == null) {
      return null;
    }
    String classPath = datasourceNode.getValueMap().get("classPath", String.class);
    if (classPath == null || classPath.isEmpty()) {
      return null;
    }
    try {
      Class<?> clazz = Class.forName(classPath);
      Object adapted = null;
      if (slingRequest != null) {
        adapted = slingRequest.adaptTo(clazz);
      }
      if (adapted == null) {
        adapted = resource.adaptTo(clazz);
      }
      componentData = (T) adapted;
    } catch (ClassNotFoundException e) {
      // classPath does not resolve to a loaded class — return null
    }
    return componentData;
  }

  @Nullable
  @Override
  public String getId() {
    T data = getComponentData();
    return data != null ? data.getId() : null;
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    T data = getComponentData();
    if (data != null) {
      return data.getComponentVariationRetrievalService();
    }
    return super.getComponentVariationRetrievalService();
  }

  @Override
  public String getLayout(String propertyName) {
    T data = getComponentData();
    return data != null ? data.getLayout(propertyName) : super.getLayout(propertyName);
  }

  @Override
  public List<ComponentVariation> getElementVariations(String propertyName, String componentType) {
    T data = getComponentData();
    return data != null ? data.getElementVariations(propertyName, componentType)
        : super.getElementVariations(propertyName, componentType);
  }

  @Nullable
  @Override
  public String getForcedResourceName() {
    T data = getComponentData();
    return data != null ? data.getForcedResourceName() : super.getForcedResourceName();
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    T data = getComponentData();
    if (data != null) {
      return data.getComponentUiFrameworkViewRetrievalService();
    }
    return super.getComponentUiFrameworkViewRetrievalService();
  }

  @Override
  public List<ComponentVariation> getVariations() {
    T data = getComponentData();
    return data != null ? data.getVariations() : super.getVariations();
  }

  @Override
  public String getLayout() {
    T data = getComponentData();
    return data != null ? data.getLayout() : super.getLayout();
  }

  @Override
  public String getParentPath() {
    return getResource().getParent().getPath();
  }

  @Override
  public Resource toSyntheticResource(@Nonnull ResourceResolver resourceResolver,
      @Nonnull String parentPath) {
    if (syntheticResource == null) {
      ResourceMetadata resourceMetadata = new ResourceMetadata();
      String name = "child-" + java.util.UUID.randomUUID();
      if (getForcedResourceName() != null && !getForcedResourceName().isEmpty()) {
        name = getForcedResourceName();
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
}