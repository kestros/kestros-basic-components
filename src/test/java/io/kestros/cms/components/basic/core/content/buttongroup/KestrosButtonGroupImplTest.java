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
package io.kestros.cms.components.basic.core.content.buttongroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosButtonGroupImplTest extends BaseSyntheticTest {

  private KestrosButtonGroupImpl buttonGroup;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    List<KestrosButton> buttons = new ArrayList<>();
    buttons.add(new KestrosButtonImpl(
        "Button 1", "/content/page1.html",
        null, AnchorTarget.SAME_WINDOW, null, null, null, null, false,
        dataSource, "button", "button1"));
    buttons.add(new KestrosButtonImpl(
        "Button 2", "/content/page2.html",
        null, AnchorTarget.SAME_WINDOW, null, null, null, null, false,
        dataSource, "button", "button2"));

    buttonGroup = new KestrosButtonGroupImpl(buttons, dataSource, "buttonGroup",
        "buttonGroupElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = buttonGroup.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/buttonGroupElement", syntheticResource.getPath());
  }

  @Test
  public void testGetButtonsElements() {
    assertEquals(2, buttonGroup.getButtonsElements().size());
  }

  @Test
  public void testGetButtonVariations() {
    assertNotNull(buttonGroup.getButtonVariations());
  }
}
