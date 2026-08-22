package io.kestros.cms.components.basic.core;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import java.util.HashMap;

/**
 * ResourceWrapper that allows for synthetic children to be added to the wrapped Resource.
 */
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
