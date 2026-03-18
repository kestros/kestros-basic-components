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
package io.kestros.cms.components.basic.core.content.button;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosButtonImplTest extends BaseSyntheticTest {

  private KestrosButtonImpl button;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    button = new KestrosButtonImpl(
        "Click Me",
        "/content/page.html",
        "Button Title",
        AnchorTarget.SAME_WINDOW,
        "nofollow",
        "aria-button",
        "describedby-id",
        "en",
        false,
        dataSource,
        "button",
        "buttonElement"
    );
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = button.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/buttonElement", syntheticResource.getPath());
  }

  @Test
  public void testGetText() {
    assertEquals("Click Me", button.getText());
  }

  @Test
  public void testGetHref() {
    assertEquals("/content/page.html", button.getHref());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Button Title", button.getTitle());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, button.getTarget());
  }

  @Test
  public void testGetRel() {
    assertEquals("nofollow", button.getRel());
  }

  @Test
  public void testGetAriaLabel() {
    assertEquals("aria-button", button.getAriaLabel());
  }

  @Test
  public void testGetAriaDescribedBy() {
    assertEquals("describedby-id", button.getAriaDescribedBy());
  }

  @Test
  public void testGetLang() {
    assertEquals("en", button.getLang());
  }

  @Test
  public void testIsDisabled() {
    assertFalse(button.isDisabled());
  }
}
