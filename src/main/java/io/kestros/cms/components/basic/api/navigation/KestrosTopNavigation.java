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