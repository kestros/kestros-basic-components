package io.kestros.cms.components.basic.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.DataSourceComponent;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public abstract class BaseDataSourceComponent<T extends KestrosBasicComponentElement>
    extends DataSourceComponent<T> implements KestrosBasicComponentElement {

  private Resource syntheticResource;

  @Nullable
  @Override
  public String getId() {
    return getComponentData().getId();
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return getComponentData().getComponentVariationRetrievalService();
  }

  @Nonnull
  @Override
  public String getLayout(@Nonnull String propertyName) {
    return getComponentData().getLayout(propertyName);
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getElementVariations(@Nonnull String propertyName,
      @Nonnull String componentType) {
    return getComponentData().getElementVariations(propertyName, componentType);
  }

  @Nullable
  @Override
  public String getForcedResourceName() {
    return getComponentData().getForcedResourceName();
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return getComponentData().getComponentUiFrameworkViewRetrievalService();
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getVariations() {
    return getComponentData().getVariations();
  }

  @Nonnull
  @Override
  public String getLayout() {
    return getComponentData().getLayout();
  }

  @Nonnull
  @Override
  public Resource toSyntheticResource(@Nonnull ResourceResolver resourceResolver,
      @Nonnull String parentPath) {
    // TODO remove duplicate
    if (syntheticResource == null) {
      ResourceMetadata resourceMetadata = new ResourceMetadata();
      String name = "child-" + java.util.UUID.randomUUID();
      final String forcedResourceName = getForcedResourceName();
      if (forcedResourceName != null && !forcedResourceName.isEmpty()) {
        name = forcedResourceName;
      }
      String path = parentPath + "/" + name;
      if (!path.startsWith("/synthetics")) {
        path = "/synthetics" + path;
      }
      resourceMetadata.setResolutionPath(path);
      resourceMetadata.setModificationTime(System.currentTimeMillis());
      Map<String, String> parameters = new HashMap<>(0);
      resourceMetadata.setParameterMap(parameters);
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> props = objectMapper.convertValue(this, Map.class);
      props.put("sling:resourceType", getComponentResourceType());
      props.put("jcr:primaryType", "nt:unstructured");
      syntheticResource = new ValueMapBackedSyntheticResource(resourceResolver, resourceMetadata,
          getComponentResourceType(), props);
      if (this instanceof KestrosContainerElement) {
        KestrosContainerElement container = (KestrosContainerElement) this;
        List<KestrosBasicComponentElement> childElements = container.getChildElements();
        Map<String, Resource> childResources =
            new LinkedHashMap<>((int) (childElements.size() / 0.75f) + 1);
        for (KestrosBasicComponentElement child : childElements) {
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