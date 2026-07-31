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

package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosTable} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableStaticDataSource extends BaseContainerSlingModelDataSource
        implements KestrosTable {

  @Nonnull
  @Override
  public List<KestrosTableHeader> getHeaderElements() {
    List<KestrosTableHeader> headers = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (KestrosTableHeader.RESOURCE_TYPE.equals(childResource.getResourceType())) {
        KestrosTableHeader header = childResource.adaptTo(TableHeaderStaticDataSource.class);
        if (header != null) {
          headers.add(header);
        }
      }
    }
    return new ArrayList<>(headers);
  }

  @Nonnull
  @Override
  public List<KestrosTableRow> getRowElements() {
    List<KestrosTableRow> rows = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (KestrosTableRow.RESOURCE_TYPE.equals(childResource.getResourceType())) {
        KestrosTableRow row = childResource.adaptTo(TableRowStaticDataSource.class);
        if (row != null) {
          rows.add(row);
        }
      }
    }
    return new ArrayList<>(rows);
  }
}
