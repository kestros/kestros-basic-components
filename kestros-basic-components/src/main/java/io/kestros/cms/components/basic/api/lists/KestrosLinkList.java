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

package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * List of links, built from the entries configured on it.
 */
public interface KestrosLinkList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/link-list";

  /**
   * Resource type the link list renders as.
   *
   * @return Resource type the link list renders as.
   */
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resources backing the links in the list.
   *
   * @return Resources backing the links in the list.
   */
  @Nonnull
  default List<Resource> getLinks() {
    List<Resource> links = new ArrayList<>();
    for (KestrosLink link : getLinkElements()) {
      links.add(link.getResource());
    }
    return links;
  }

  /**
   * Links the list holds.
   *
   * @return Links the list holds.
   */
  @Nonnull
  List<KestrosLink> getLinkElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getLinkElements());
  }
}
