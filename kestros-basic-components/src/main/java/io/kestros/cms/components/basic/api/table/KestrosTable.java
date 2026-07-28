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
 * API for the table component element.
 */
public interface KestrosTable extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Header elements.
   *
   * @return Header elements.
   */
  @Nonnull
  List<KestrosTableHeader> getHeaderElements();

  /**
   * Headers.
   *
   * @return Headers.
   */
  @Nonnull
  default List<Resource> getHeaders() {
    final List<KestrosTableHeader> sourceHeaders = getHeaderElements();
    final List<Resource> headers = new ArrayList<>(sourceHeaders.size());
    for (KestrosTableHeader header : sourceHeaders) {
      if (header.isSynthetic()) {
        headers.add(header.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        headers.add(header.getResource());
      }
    }
    return headers;
  }

  /**
   * Row elements.
   *
   * @return Row elements.
   */
  @Nonnull
  List<KestrosTableRow> getRowElements();

  /**
   * Rows.
   *
   * @return Rows.
   */
  @Nonnull
  default List<Resource> getRows() {
    final List<KestrosTableRow> sourceRows = getRowElements();
    final List<Resource> rows = new ArrayList<>(sourceRows.size());
    for (KestrosTableRow row : sourceRows) {
      if (row.isSynthetic()) {
        rows.add(row.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        rows.add(row.getResource());
      }
    }
    return rows;
  }

  /**
   * Child elements.
   *
   * @return Child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getRowElements());
  }
}
