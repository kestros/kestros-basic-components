package io.kestros.cms.components.basic.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
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
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class PropertyBackedSyntheticResource extends SyntheticResource {

  private final Map<String, Object> properties;

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
    this.properties = new HashMap<>(properties);
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
    return new ValueMapDecorator(new HashMap<>(properties));
  }
}
