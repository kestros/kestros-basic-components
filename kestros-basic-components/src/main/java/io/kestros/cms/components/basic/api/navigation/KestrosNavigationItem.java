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
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * One entry in a navigation.
 */
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
   * @return Whether this item points at the page currently being requested.
   */
  Boolean isActive();

  /**
   * Navigation items nested beneath this one.
   *
   * @return Navigation items nested beneath this one.
   */
  @Nonnull
  List<KestrosNavigationItem> getNavigationItems();

  /**
   * Resources backing the nested navigation items.
   *
   * @return Resources backing the nested navigation items.
   */
  @Nonnull
  default List<Resource> getNavigationItemLinks() {
    List<Resource> resources = new ArrayList<>();
    for (KestrosNavigationItem item : getNavigationItems()) {
      resources.add(item.getResource());
    }
    return resources;
  }

  /**
   * Nested navigation items, as generic child elements.
   *
   * @return Nested navigation items, as generic child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationItems());
  }
}
