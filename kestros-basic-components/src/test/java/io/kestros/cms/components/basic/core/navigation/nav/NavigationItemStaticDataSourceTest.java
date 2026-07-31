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

package io.kestros.cms.components.basic.core.navigation.nav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class NavigationItemStaticDataSourceTest extends BaseDataSourceTest {

  private NavigationItemStaticDataSource dataSource;

  @Override
  public void doComponentSetup() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("text", "Products");
    properties.put("href", "/content/products");
    final Resource resource =
        context.create().resource("/content/page/jcr:content/nav-item", properties);
    context.request().setResource(resource);
    dataSource = context.request().adaptTo(NavigationItemStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertEquals(KestrosNavigationItem.RESOURCE_TYPE,
        dataSource.toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content")
            .getResourceType());
  }

  /**
   * isActive returned null from a method the interface declares @Nonnull, so a Java caller writing
   * {@code if (item.isActive())} unboxed a null and threw. It has to be a real Boolean now.
   *
   * <p>Nothing computes active state yet, so FALSE is the only correct answer; this asserts the
   * contract, not the feature.</p>
   */
  @Test
  public void testIsActiveIsNeverNull() {
    assertFalse(dataSource.isActive());
  }

  @Test
  public void testGetNavigationItemsIsEmpty() {
    assertTrue(dataSource.getNavigationItems().isEmpty());
  }

  @Test
  public void testGetText() {
    assertEquals("Products", dataSource.getText());
  }
}
