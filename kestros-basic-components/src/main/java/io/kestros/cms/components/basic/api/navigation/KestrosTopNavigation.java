package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

public interface KestrosTopNavigation extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/top-navigation";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  default List<Resource> getNavigationLinks() {
    List<Resource> resources = new ArrayList<>();
    for (KestrosTopNavigationItem item : getNavigationLinkElements()) {
      resources.add(item.getResource());
    }
    return resources;
  }

  @Nonnull
  List<KestrosTopNavigationItem> getNavigationLinkElements();

  @Nullable
  String getBrandName();

  @Nullable
  KestrosImage getImageElement();

  @Nullable
  default Resource getImage() {
    if (getImageElement() != null) {
      return getImageElement().getResource();
    }
    return null;
  }

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationLinkElements());
  }

}