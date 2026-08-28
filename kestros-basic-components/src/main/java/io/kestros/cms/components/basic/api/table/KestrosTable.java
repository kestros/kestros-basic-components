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

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Table element, holding a row of headers and the rows beneath them.
 */
public interface KestrosTable extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table";

  /**
   * Resource type the table renders as.
   *
   * @return Resource type the table renders as.
   */
  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Header elements of the table.
   *
   * @return Header elements of the table.
   */
  @Nonnull
  List<KestrosTableHeader> getHeaderElements();

  /**
   * Resources for the table's headers, built synthetically where the header has no stored
   * resource.
   *
   * @return Resources for the table's headers.
   */
  @Nonnull
  default List<Resource> getHeaders() {
    List<Resource> headers = new ArrayList<>();
    for (KestrosTableHeader header : getHeaderElements()) {
      if (header.isSynthetic()) {
        headers.add(header.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        headers.add(header.getResource());
      }
    }
    return headers;
  }

  /**
   * Row elements of the table.
   *
   * @return Row elements of the table.
   */
  @Nonnull
  List<KestrosTableRow> getRowElements();

  /**
   * Resources for the table's rows, built synthetically where the row has no stored resource.
   *
   * @return Resources for the table's rows.
   */
  @Nonnull
  default List<Resource> getRows() {
    List<Resource> rows = new ArrayList<>();
    for (KestrosTableRow row : getRowElements()) {
      if (row.isSynthetic()) {
        rows.add(row.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        rows.add(row.getResource());
      }
    }
    return rows;
  }

  /**
   * Rows of the table, as generic child elements.
   *
   * @return Rows of the table, as generic child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getRowElements());
  }
}
