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
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic (synthetic) {@link KestrosTableCell}, letting a datasource build table cells in code
 * rather than from authored child resources. Mirrors
 * {@link io.kestros.cms.components.basic.core.content.card.KestrosCardImpl}. A cell is either simple
 * text or a list of rendered content elements (e.g. a link or image).
 */
public class KestrosTableCellImpl extends BaseContainerSyntheticResource implements KestrosTableCell {

  private final String text;
  private final List<KestrosBasicComponentElement> cellContentElements;

  /**
   * Text-only cell.
   *
   * @param text                cell text.
   * @param dataSource          owning datasource.
   * @param resourcePrefix      resource prefix (for variation/layout resolution).
   * @param forcedResourceName  synthetic resource name (unique within the row).
   * @throws ComponentConfigurationException if the synthetic resource cannot be configured.
   */
  public KestrosTableCellImpl(@Nullable final String text,
      @Nonnull final BaseSlingModelDataSource dataSource, @Nonnull final String resourcePrefix,
      @Nullable final String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = text;
    this.cellContentElements = new ArrayList<>();
  }

  /**
   * Cell containing rendered content elements (e.g. a link or image).
   *
   * @param cellContentElements the elements rendered inside the cell.
   * @param dataSource          owning datasource.
   * @param resourcePrefix      resource prefix (for variation/layout resolution).
   * @param forcedResourceName  synthetic resource name (unique within the row).
   * @throws ComponentConfigurationException if the synthetic resource cannot be configured.
   */
  public KestrosTableCellImpl(@Nonnull final List<KestrosBasicComponentElement> cellContentElements,
      @Nonnull final BaseSlingModelDataSource dataSource, @Nonnull final String resourcePrefix,
      @Nullable final String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = null;
    this.cellContentElements = new ArrayList<>(cellContentElements);
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    return new ArrayList<>(cellContentElements);
  }
}
