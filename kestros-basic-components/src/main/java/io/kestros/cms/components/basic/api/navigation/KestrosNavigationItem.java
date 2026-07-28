package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * API for the navigation item component element.
 */
public interface KestrosNavigationItem extends KestrosLink, KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/navigation-item";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Active.
   *
   * @return Active.
   */
  @Nonnull
  Boolean isActive();

  /**
   * Navigation items.
   *
   * @return Navigation items.
   */
  @Nonnull
  List<KestrosNavigationItem> getNavigationItems();

  /**
   * Navigation item links.
   *
   * @return Navigation item links.
   */
  @Nonnull
  default List<Resource> getNavigationItemLinks() {
    final List<KestrosNavigationItem> sourceResources = getNavigationItems();
    final List<Resource> resources = new ArrayList<>(sourceResources.size());
    for (KestrosNavigationItem item : sourceResources) {
      resources.add(item.getResource());
    }
    return resources;
  }

  /**
   * Child elements.
   *
   * @return Child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationItems());
  }
}
