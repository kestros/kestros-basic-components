package io.kestros.cms.components.basic.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

public abstract class BaseComponentElement implements KestrosBasicComponentElement {
  private Resource syntheticResource;

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
}
