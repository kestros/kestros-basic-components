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
 * Single cell of a table row.
 */
public interface KestrosTableCell extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-cell";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resources backing the elements the cell contains.
   *
   * @return Resources backing the elements the cell contains.
   */
  @JsonIgnore
  @Nonnull
  default List<Resource> getCellContent() {
    List<Resource> cellContent = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getCellContentElements()) {
      cellContent.add(element.getResource());
    }
    return cellContent;
  }

  /**
   * Text shown in the cell, where the cell holds text rather than elements.
   *
   * @return Text shown in the cell, or null where the cell holds elements.
   */
  @javax.annotation.Nullable
  default String getText() {
    return null;
  }

  /**
   * Elements the cell contains.
   *
   * @return Elements the cell contains.
   */
  @Nonnull
  List<KestrosBasicComponentElement> getCellContentElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return getCellContentElements();
  }
}
