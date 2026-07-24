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
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosTopNavigationImplTest extends BaseSyntheticTest {

  private KestrosTopNavigationImpl topNavigation;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    List<KestrosTopNavigationItem> items = new ArrayList<>();
    topNavigation = new KestrosTopNavigationImpl("Brand Name", null, items, dataSource,
        "topNavigation", "topNavigationElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = topNavigation.toSyntheticResource(getResourceResolver(),
        "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/topNavigationElement", syntheticResource.getPath());
  }

  @Test
  public void testGetNavigationLinkElements() {
    assertNotNull(topNavigation.getNavigationLinkElements());
    assertEquals(0, topNavigation.getNavigationLinkElements().size());
  }

  @Test
  public void testGetBrandName() {
    assertEquals("Brand Name", topNavigation.getBrandName());
  }

  @Test
  public void testGetImageElement() {
    assertNull(topNavigation.getImageElement());
  }
}
