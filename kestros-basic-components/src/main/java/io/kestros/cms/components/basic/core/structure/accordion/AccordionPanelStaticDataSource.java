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

import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Datasource that builds an accordion panel from the resource it is adapted from.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AccordionPanelStaticDataSource extends BaseSlingModelDataSource
    implements KestrosAccordionPanel {
  @Nullable
  @Override
  public String getTitle() {
    return getResource().getValueMap().get("panelTitle", String.class);
  }

  @Nonnull
  @Override
  public Boolean isExpanded() {
    return getResource().getValueMap().get("panelExpanded", false);
  }

  @Nonnull
  @Override
  public Boolean isDisabled() {
    return getResource().getValueMap().get("panelDisabled", false);
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getResource().getValueMap().get("panelAriaLabel", String.class);
  }
}
