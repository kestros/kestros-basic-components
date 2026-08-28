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
 * Breadcrumb trail leading to the page being rendered.
 */
public interface KestrosBreadCrumbs extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/breadcrumbs";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resources backing the crumbs in the trail.
   *
   * @return Resources backing the crumbs in the trail.
   */
  @Nonnull
  default List<Resource> getLinks() {
    List<Resource> links = new ArrayList<>();
    for (KestrosBreadCrumb link : getLinkElements()) {
      links.add(link.getResource());
    }
    return links;
  }

  /**
   * Crumbs the trail holds.
   *
   * @return Crumbs the trail holds.
   */
  @Nonnull
  List<KestrosBreadCrumb> getLinkElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getLinkElements());
  }
}
