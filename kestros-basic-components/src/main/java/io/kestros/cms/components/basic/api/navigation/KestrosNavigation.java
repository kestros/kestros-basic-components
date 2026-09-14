package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosNavigation extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/navigation";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  default List<Resource> getNavigationLinks() {
    final List<KestrosNavigationItem> sourceResources = getNavigationLinkElements();
    final List<Resource> resources = new ArrayList<>(sourceResources.size());
    for (KestrosNavigationItem item : sourceResources) {
      resources.add(item.getResource());
    }
    return resources;
  }


  @Nonnull
  List<KestrosNavigationItem> getNavigationLinkElements();

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationLinkElements());
  }
}
