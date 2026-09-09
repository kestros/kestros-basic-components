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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Navigation element, holding the items it lists.
 */
public interface KestrosNavigation extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/navigation";

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
    List<KestrosNavigationItem> linkElements = getNavigationLinkElements();
    List<Resource> resources = new ArrayList<>(linkElements.size());
    for (KestrosNavigationItem item : linkElements) {
      resources.add(item.getResource());
    }
    return resources;
  }


  /**
   * Navigation items the navigation holds.
   *
   * @return Navigation items the navigation holds.
   */
  @Nonnull
  List<KestrosNavigationItem> getNavigationLinkElements();

  /**
   * Navigation items, as generic child elements.
   *
   * @return Navigation items, as generic child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationLinkElements());
  }
}
