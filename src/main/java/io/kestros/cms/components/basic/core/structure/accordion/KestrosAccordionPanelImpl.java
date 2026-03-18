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

package io.kestros.cms.components.basic.core.structure.accordion;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosAccordionPanelImpl extends BaseSyntheticResource
    implements KestrosAccordionPanel {

  private final String title;
  private final Boolean expanded;
  private final Boolean disabled;
  private final String ariaLabel;

  public KestrosAccordionPanelImpl(
      @Nonnull String title,
      @Nonnull Boolean expanded,
      @Nonnull Boolean disabled,
      @Nonnull String ariaLabel,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.title = title;
    this.expanded = expanded;
    this.disabled = disabled;
    this.ariaLabel = ariaLabel;
  }

  @Nullable
  @Override
  public String getTitle() {
    return title;
  }

  @Nonnull
  @Override
  public Boolean isExpanded() {
    return expanded;
  }

  @Nonnull
  @Override
  public Boolean isDisabled() {
    return disabled;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return ariaLabel;
  }
}