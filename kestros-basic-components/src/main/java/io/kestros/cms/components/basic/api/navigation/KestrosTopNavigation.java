/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
 * Top navigation element, holding the navigation items and the branding shown alongside them.
 */
public interface KestrosTopNavigation extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/top-navigation";

  /**
   * Resource type the top navigation renders as.
   *
   * @return Resource type the top navigation renders as.
   */
  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resources backing the navigation items.
   *
   * @return Resources backing the navigation items.
   */
  @Nonnull
  default List<Resource> getNavigationLinks() {
    List<KestrosTopNavigationItem> linkElements = getNavigationLinkElements();
    List<Resource> resources = new ArrayList<>(linkElements.size());
    for (KestrosTopNavigationItem item : linkElements) {
      resources.add(item.getResource());
    }
    return resources;
  }

  /**
   * Navigation items the top navigation holds.
   *
   * @return Navigation items the top navigation holds.
   */
  @Nonnull
  List<KestrosTopNavigationItem> getNavigationLinkElements();

  /**
   * Brand name shown in the navigation, if one was configured.
   *
   * @return Brand name shown in the navigation, if one was configured.
   */
  @Nullable
  String getBrandName();

  /**
   * Image element used as the branding, if one was configured.
   *
   * @return Image element used as the branding, if one was configured.
   */
  @Nullable
  KestrosImage getImageElement();

  /**
   * Resource backing the branding image, if one was configured.
   *
   * @return Resource backing the branding image, if one was configured.
   */
  @Nullable
  default Resource getImage() {
    if (getImageElement() != null) {
      return getImageElement().getResource();
    }
    return null;
  }

  /**
   * Navigation items, as generic child elements.
   *
   * @return Navigation items, as generic child elements.
   */
  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationLinkElements());
  }

}
