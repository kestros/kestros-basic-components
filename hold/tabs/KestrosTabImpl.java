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
package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabContent;
import io.kestros.cms.components.basic.api.structure.KestrosTabHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosTabImpl extends BaseSyntheticResource implements KestrosTab {
  private KestrosTabHeader tabHeader;
  private KestrosTabContent tabContent;

  public KestrosTabImpl(
      @Nonnull KestrosTabHeader tabHeader,
      @Nonnull KestrosTabContent tabContent,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix,
      @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.tabHeader = tabHeader;
    this.tabContent = tabContent;
  }


  @Override
  public KestrosTabHeader getTabHeaderElement() {
    return tabHeader;
  }

  @Override
  public KestrosTabContent getTabContentElement() {
    return tabContent;
  }
}
