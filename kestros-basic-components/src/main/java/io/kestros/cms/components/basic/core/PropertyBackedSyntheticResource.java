package io.kestros.cms.components.basic.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

/**
 * A synthetic resource whose properties come from a supplied map rather than from the repository.
 *
 * <p>Both synthetic-resource builders used an anonymous subclass for this, which captured the
 * enclosing component for no reason and could carry no nullability contract of its own.</p>
 */
public class PropertyBackedSyntheticResource extends SyntheticResource {

  private final ValueMap valueMap;

  /**
   * A synthetic resource whose properties come from a supplied map.
   *
   * @param resourceResolver Resolver the synthetic resource belongs to.
   * @param resourceMetadata Metadata describing where the resource sits.
   * @param resourceType Resource type the synthetic resource reports.
   * @param properties Properties the resource exposes. Copied, so a later change by the caller
   *     cannot rewrite what the resource reports.
   */
  public PropertyBackedSyntheticResource(@Nonnull final ResourceResolver resourceResolver,
      @Nonnull final ResourceMetadata resourceMetadata, @Nonnull final String resourceType,
      @Nonnull final Map<String, Object> properties) {
    super(resourceResolver, resourceMetadata, resourceType);
    this.valueMap = new ValueMapDecorator(new HashMap<>(properties));
  }

  /**
   * The properties the resource reports.
   *
   * @return A fresh value map over a copy of the properties. Writing to it changes nothing the
   *     resource reports, and one caller's writes are invisible to the next.
   */
  @Override
  @Nonnull
  public ValueMap getValueMap() {
    return new ValueMapDecorator(new HashMap<>(valueMap));
  }

  /**
   * Whether another object is a synthetic resource reporting the same thing as this one.
   *
   * @param other Object to compare against.
   * @return True when the other object is a PropertyBackedSyntheticResource at the same path, of
   *     the same resource type, reporting the same properties.
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof PropertyBackedSyntheticResource)) {
      return false;
    }
    final PropertyBackedSyntheticResource that = (PropertyBackedSyntheticResource) other;
    return Objects.equals(getPath(), that.getPath())
        && Objects.equals(getResourceType(), that.getResourceType())
        && Objects.equals(valueMap, that.valueMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPath(), getResourceType(), valueMap);
  }

  /**
   * Describes the resource for a log line.
   *
   * @return The resource's path, type, and how many properties it reports. The property values
   *     are not included: they are author content and can be arbitrarily large.
   */
  @Override
  @Nonnull
  public String toString() {
    return "PropertyBackedSyntheticResource{path=" + getPath()
        + ", resourceType=" + getResourceType()
        + ", properties=" + valueMap.size() + "}";
  }
}
