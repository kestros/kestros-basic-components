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

package io.kestros.cms.components.basic.core.structure.grid;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Implementation of the grid element.
 */
public class KestrosGridImpl extends BaseContainerSyntheticResource implements KestrosGrid {

  private List<KestrosContainer> columns;

  /**
   * Builds a grid from the columns it is given.
   *
   * @param columns Columns the grid holds.
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
   */
  public KestrosGridImpl(
      @Nonnull List<KestrosContainer> columns,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.columns = columns;
  }

}
