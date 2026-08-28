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

  @Nonnull
  Boolean isActive();

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
