package io.kestros.cms.components.basic.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;

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
  public SyntheticResourceWrapper(@Nonnull Resource wrapped,
      @Nonnull Map<String, Resource> children) {
    super(wrapped);
    // Copied rather than held: the caller keeps its own reference to the map it passed, and a
    // later put on it would silently add a child to a Resource that had already been handed out.
    this.children = new LinkedHashMap<>(children);
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

  /**
   * Two wrappers are the same when they wrap the same Resource and carry the same synthetic
   * children. ResourceWrapper does not define equals, so wrappers around one Resource compared as
   * different and could both sit in the same collection.
   *
   * @param other Object to compare against.
   * @return True when both wrap an equal Resource and hold equal children.
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof SyntheticResourceWrapper)) {
      return false;
    }
    final SyntheticResourceWrapper that = (SyntheticResourceWrapper) other;
    return Objects.equals(getResource(), that.getResource())
        && Objects.equals(this.children, that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getResource(), children);
  }

  @Nonnull
  @Override
  public String toString() {
    return "SyntheticResourceWrapper{path=" + getPath() + ", children=" + children.keySet() + "}";
  }
}
