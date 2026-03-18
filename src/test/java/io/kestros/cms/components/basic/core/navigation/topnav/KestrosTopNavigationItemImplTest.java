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

package io.kestros.cms.components.basic.core.navigation.topnav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosTopNavigationItemImplTest extends BaseSyntheticTest {

  private KestrosTopNavigationItemImpl topNavItem;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    KestrosLinkImpl link = new KestrosLinkImpl("Products", "/content/products.html", "Products",
        AnchorTarget.SAME_WINDOW, null, "Products aria", null, "en",
        dataSource, "topNavItem", "topNavItemLink");

    List<KestrosTopNavigationItem> children = new ArrayList<>();
    topNavItem = new KestrosTopNavigationItemImpl(link, children, dataSource, "topNavItem",
        "topNavItemElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = topNavItem.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/topNavItemElement", syntheticResource.getPath());
  }

  @Test
  public void testGetNavigationItems() {
    assertNotNull(topNavItem.getNavigationItems());
    assertEquals(0, topNavItem.getNavigationItems().size());
  }

  @Test
  public void testGetText() {
    assertEquals("Products", topNavItem.getText());
  }

  @Test
  public void testGetHref() {
    assertEquals("/content/products.html", topNavItem.getHref());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, topNavItem.getTarget());
  }

  @Test
  public void testGetAriaLabel() {
    assertEquals("Products aria", topNavItem.getAriaLabel());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Products", topNavItem.getTitle());
  }

  @Test
  public void testGetLang() {
    assertEquals("en", topNavItem.getLang());
  }
}
