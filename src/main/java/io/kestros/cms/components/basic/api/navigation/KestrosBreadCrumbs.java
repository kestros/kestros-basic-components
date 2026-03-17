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

public interface KestrosBreadCrumbs extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/breadcrumbs";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  default List<Resource> getLinks() {
    List<Resource> links = new ArrayList<>();
    for (KestrosBreadCrumb link : getLinkElements()) {
      links.add(link.getResource());
    }
    return links;
  }

  @Nonnull
  List<KestrosBreadCrumb> getLinkElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getLinkElements());
  }
}
