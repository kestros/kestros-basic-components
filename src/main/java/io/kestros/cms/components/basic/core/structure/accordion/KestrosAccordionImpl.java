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
import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosAccordionImpl extends BaseContainerSyntheticResource
    implements KestrosAccordion {

  private List<KestrosAccordionPanel> panels;

  public KestrosAccordionImpl(
      @Nonnull List<KestrosAccordionPanel> panels,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
  }


  @Nonnull
  @Override
  public List<KestrosAccordionPanel> getPanelElements() {
    return new ArrayList<>(panels);
  }
}
