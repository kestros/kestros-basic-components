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
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosAccordionImplTest extends BaseSyntheticTest {

  private KestrosAccordionImpl accordion;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    List<KestrosAccordionPanel> panels = new ArrayList<>();
    panels.add(new KestrosAccordionPanelImpl("Panel 1", true, false, "Panel 1 Aria", dataSource,
        "accordionPanel", "panel1"));
    panels.add(new KestrosAccordionPanelImpl("Panel 2", false, false, "Panel 2 Aria", dataSource,
        "accordionPanel", "panel2"));

    accordion = new KestrosAccordionImpl(panels, dataSource, "accordion", "accordionElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = accordion.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/accordionElement", syntheticResource.getPath());
  }

  @Test
  public void testGetPanelElements() {
    assertNotNull(accordion.getPanelElements());
    assertEquals(2, accordion.getPanelElements().size());
  }

  @Test
  public void testGetPanelElementsIsDefensiveCopy() {
    List<KestrosAccordionPanel> panels = accordion.getPanelElements();
    panels.clear();
    assertEquals(2, accordion.getPanelElements().size());
  }
}
