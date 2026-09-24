package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosNavigationItem extends KestrosLink, KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/navigation-item";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Whether this item points at the page currently being requested.
   *
   * <p>Both implementations returned null from this, which is declared @Nonnull, so any caller
   * unboxing it got a NullPointerException. The body is the one KestrosTopNavigationItem already
   * carried: the sibling interface had working logic while this one had none.
   *
   * @return True when the item's href matches the current request URI.
   */
  @Nonnull
  default Boolean isActive() {
    if (getRequest() != null) {
      String currentPath = getRequest().getRequestURI();
      String linkPath = getHref();
      if (linkPath != null && !linkPath.isBlank()) {
        return currentPath.equals(linkPath);
      }
    }
    return Boolean.FALSE;
  }

  @Nonnull
  List<KestrosNavigationItem> getNavigationItems();

  @Nonnull
  default List<Resource> getNavigationItemLinks() {
    List<KestrosNavigationItem> navigationItems = getNavigationItems();
    List<Resource> resources = new ArrayList<>(navigationItems.size());
    for (KestrosNavigationItem item : navigationItems) {
      resources.add(item.getResource());
    }
    return resources;
  }

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationItems());
  }
}
