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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Row of a table, holding its cells.
 */
public interface KestrosTableRow extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-row";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }


  /**
   * Cells the row holds.
   *
   * @return Cells the row holds.
   */
  @Nonnull
  List<KestrosTableCell> getCellElements();

  /**
   * Resources backing the cells in the row.
   *
   * @return Resources backing the cells in the row.
   */
  @JsonIgnore
  @Nonnull
  default List<Resource> getCells() {
    List<Resource> cells = new ArrayList<>();
    for (KestrosTableCell cell : getCellElements()) {
      if (cell.isSynthetic()) {
        cells.add(cell.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        cells.add(cell.getResource());
      }
    }
    return cells;
  }

  /**
   * Cells of the row, as generic child elements.
   *
   * @return Cells of the row, as generic child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getCellElements());
  }
}
