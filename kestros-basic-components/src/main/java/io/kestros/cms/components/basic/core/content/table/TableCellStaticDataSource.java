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

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Datasource that builds a table cell from the resource it is adapted from.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableCellStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTableCell {


  @Nonnull
  @Override
  public List<Resource> getCellContent() {
    List<Resource> cellContent = new java.util.ArrayList<>();
    for (Resource child : getResource().getChildren()) {
      cellContent.add(child);
    }
    return cellContent;
  }


  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    List<KestrosBasicComponentElement> cellContentElements = new ArrayList<>();
    // This should never get hit, since we can just look to child resources instead of
    // synthesizing them, but we need to return something here to satisfy the interface
    // contract.
    return new ArrayList<>();
  }

  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
