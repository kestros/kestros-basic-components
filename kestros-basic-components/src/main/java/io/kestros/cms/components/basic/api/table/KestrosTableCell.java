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

package io.kestros.cms.components.basic.api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * API for the table cell component element.
 */
public interface KestrosTableCell extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-cell";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Cell content.
   *
   * @return Cell content.
   */
  @JsonIgnore
  @Nonnull
  default List<Resource> getCellContent() {
    final List<KestrosBasicComponentElement> elements = getCellContentElements();
    final List<Resource> cellContent = new java.util.ArrayList<>(elements.size());
    for (final KestrosBasicComponentElement element : elements) {
      cellContent.add(element.getResource());
    }
    return cellContent;
  }

  /**
   * Text.
   *
   * @return Text.
   */
  @javax.annotation.Nullable
  default String getText() {
    return null;
  }

  /**
   * Cell content elements.
   *
   * @return Cell content elements.
   */
  @Nonnull
  List<KestrosBasicComponentElement> getCellContentElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return getCellContentElements();
  }
}
