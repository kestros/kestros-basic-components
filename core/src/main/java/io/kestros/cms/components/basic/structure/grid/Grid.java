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

package io.kestros.cms.components.basic.structure.grid;

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getChildAsType;

import io.kestros.cms.foundation.content.BaseComponent;
import io.kestros.cms.foundation.content.components.contentarea.ContentArea;
import io.kestros.commons.structuredslingmodels.annotation.StructuredModel;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.exceptions.InvalidResourceTypeException;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StructuredModel(validationService = GridValidationService.class)
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/structure/grid")
public class Grid extends BaseComponent {

  private static final Logger LOG = LoggerFactory.getLogger(Grid.class);

  public int getNumberOfColumns() {
    return getProperty("columns", 3);
  }

  public List<ContentArea> getColumns() {
    List<ContentArea> columns = new ArrayList<>();
    for (int i = 1; i < getNumberOfColumns() + 1; i++) {
      try {
        columns.add(getChildAsType("column-" + i, this, ContentArea.class));
      } catch (ChildResourceNotFoundException | InvalidResourceTypeException e) {
        LOG.error("Unable to render 'column-{}' under Grid '{}'. {}", i, getPath(), e.getMessage());
      }
    }
    return columns;
  }
}
