package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * API for the top navigation component element.
 */
public interface KestrosTopNavigation extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/top-navigation";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Navigation links.
   *
   * @return Navigation links.
   */
  @Nonnull
  default List<Resource> getNavigationLinks() {
    final List<KestrosTopNavigationItem> sourceResources = getNavigationLinkElements();
    final List<Resource> resources = new ArrayList<>(sourceResources.size());
    for (KestrosTopNavigationItem item : sourceResources) {
      resources.add(item.getResource());
    }
    return resources;
  }

  /**
   * Navigation link elements.
   *
   * @return Navigation link elements.
   */
  @Nonnull
  List<KestrosTopNavigationItem> getNavigationLinkElements();

  /**
   * Brand name.
   *
   * @return Brand name.
   */
  @Nullable
  String getBrandName();

  /**
   * Image element.
   *
   * @return Image element.
   */
  @Nullable
  KestrosImage getImageElement();

  /**
   * Image.
   *
   * @return Image.
   */
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