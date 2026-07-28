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
 * API for the navigation component element.
 */
public interface KestrosNavigation extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/navigation";

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
    final List<KestrosNavigationItem> sourceResources = getNavigationLinkElements();
    final List<Resource> resources = new ArrayList<>(sourceResources.size());
    for (KestrosNavigationItem item : sourceResources) {
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
  List<KestrosNavigationItem> getNavigationLinkElements();

  /**
   * Child elements.
   *
   * @return Child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationLinkElements());
  }
}
