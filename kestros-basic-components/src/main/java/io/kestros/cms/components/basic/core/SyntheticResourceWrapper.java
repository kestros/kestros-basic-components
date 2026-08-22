package io.kestros.cms.components.basic.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;

/**
 * ResourceWrapper that allows for synthetic children to be added to the wrapped Resource.
 */
@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_EQUALS",
    justification = "A ResourceWrapper. ResourceWrapper declares no equals of its own, and Sling"
        + " identifies a resource by its path rather than by which wrapper holds it.")
public class SyntheticResourceWrapper extends ResourceWrapper {

  private final Map<String, Resource> children;

  /**
   * SyntheticResourceWrapper Constructor.
   *
   * @param wrapped Wrapped Resource.
   * @param children Map of synthetic children to add to the wrapped Resource.
   */
  public SyntheticResourceWrapper(Resource wrapped, @Nonnull Map<String, Resource> children) {
    super(wrapped);
    this.children = new HashMap<>(children);
  }

  @Nullable
  @Override
  public Resource getChild(@Nullable String name) {
    Resource child = children.get(name);
    return child != null ? child : super.getChild(name);
  }

  @Nonnull
  @Override
  public Iterator<Resource> listChildren() {
    return children.values().iterator();
  }

  @Nonnull
  @Override
  public Iterable<Resource> getChildren() {
    return children.values();
  }
}
