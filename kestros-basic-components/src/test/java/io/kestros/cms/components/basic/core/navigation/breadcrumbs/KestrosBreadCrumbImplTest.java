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
package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosBreadCrumbImplTest extends BaseSyntheticTest {

  private KestrosBreadCrumbImpl breadCrumb;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    KestrosLinkImpl link = new KestrosLinkImpl("Home", "/content/home.html", "Home Page",
        AnchorTarget.SAME_WINDOW, null, "Home Label", null, "en",
        dataSource, "breadcrumb", "homeLink");

    breadCrumb = new KestrosBreadCrumbImpl(link, true, false, dataSource, "breadcrumb",
        "breadCrumbElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = breadCrumb.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/breadCrumbElement", syntheticResource.getPath());
  }

  @Test
  public void testIsFirstItem() {
    assertTrue(breadCrumb.isFirstItem());
  }

  @Test
  public void testIsLastItem() {
    assertFalse(breadCrumb.isLastItem());
  }

  @Test
  public void testGetText() {
    assertEquals("Home", breadCrumb.getText());
  }

  @Test
  public void testGetHref() {
    assertEquals("/content/home.html", breadCrumb.getHref());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Home Page", breadCrumb.getTitle());
  }

  @Test
  public void testGetAriaLabel() {
    assertEquals("Home Label", breadCrumb.getAriaLabel());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, breadCrumb.getTarget());
  }

  @Test
  public void testGetLang() {
    assertEquals("en", breadCrumb.getLang());
  }
}
