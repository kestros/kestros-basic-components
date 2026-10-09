package io.kestros.cms.components.basic.core;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.api.resource.ValueMap;

/**
 * Synthetic resource that serves a fixed set of properties.
 *
 * <p>Both element base classes built this as an anonymous subclass of SyntheticResource, in
 * identical code. An anonymous class cannot be static, so each instance held a reference back to
 * the element that made it, and neither its constructor nor its getValueMap could carry a
 * nullability annotation.
 */
class ValueMapBackedSyntheticResource extends SyntheticResource {

  private final ValueMap valueMap;

  ValueMapBackedSyntheticResource(@Nonnull final ResourceResolver resourceResolver,
      @Nonnull final ResourceMetadata resourceMetadata, @Nonnull final String resourceType,
      @Nonnull final Map<String, Object> properties) {
    super(resourceResolver, resourceMetadata, resourceType);
    this.valueMap = new ValueMapDecorator(new HashMap<>(properties));
  }

  @Nonnull
  @Override
  public ValueMap getValueMap() {
    return valueMap;
  }
}
