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

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosBreadCrumbsImplTest extends BaseSyntheticTest {

  private KestrosBreadCrumbsImpl breadCrumbs;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    KestrosLinkImpl homeLink = new KestrosLinkImpl("Home", "/content/home.html", null,
        AnchorTarget.SAME_WINDOW, null, null, null, null, dataSource, "breadcrumb", "homeLink");
    KestrosLinkImpl pageLink = new KestrosLinkImpl("Page", "/content/page.html", null,
        AnchorTarget.SAME_WINDOW, null, null, null, null, dataSource, "breadcrumb", "pageLink");

    List<KestrosBreadCrumb> crumbs = new ArrayList<>();
    crumbs.add(new KestrosBreadCrumbImpl(homeLink, true, false, dataSource, "breadcrumb", "home"));
    crumbs.add(new KestrosBreadCrumbImpl(pageLink, false, true, dataSource, "breadcrumb", "page"));

    breadCrumbs = new KestrosBreadCrumbsImpl(crumbs, dataSource, "breadcrumbs",
        "breadCrumbsElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = breadCrumbs.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/breadCrumbsElement", syntheticResource.getPath());
  }

  @Test
  public void testGetLinkElements() {
    assertNotNull(breadCrumbs.getLinkElements());
    assertEquals(2, breadCrumbs.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsIsDefensiveCopy() {
    List<KestrosBreadCrumb> crumbs = breadCrumbs.getLinkElements();
    crumbs.clear();
    assertEquals(2, breadCrumbs.getLinkElements().size());
  }
}
