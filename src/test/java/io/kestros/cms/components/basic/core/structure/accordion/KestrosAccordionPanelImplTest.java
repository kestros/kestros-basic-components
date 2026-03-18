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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosAccordionPanelImplTest extends BaseSyntheticTest {

  private KestrosAccordionPanelImpl panel;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    panel = new KestrosAccordionPanelImpl("Panel Title", true, false, "Panel Aria Label",
        dataSource, "accordionPanel", "panelElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = panel.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/panelElement", syntheticResource.getPath());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Panel Title", panel.getTitle());
  }

  @Test
  public void testIsExpanded() {
    assertTrue(panel.isExpanded());
  }

  @Test
  public void testIsDisabled() {
    assertFalse(panel.isDisabled());
  }

  @Test
  public void testGetAriaLabel() {
    assertEquals("Panel Aria Label", panel.getAriaLabel());
  }
}
