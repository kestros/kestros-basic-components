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

package io.kestros.cms.components.basic.core.content.icon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class IconStaticDataSourceTest extends BaseDataSourceTest {

  private IconStaticDataSource iconStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    properties.put("iconType", "svg");
    properties.put("iconClass", "fa fa-star");
    properties.put("iconPath", "/content/dam/icons/star.svg");
    properties.put("altText", "A star icon");
    properties.put("hideFromScreenReader", true);
    properties.put("href", "https://example.com");
    properties.put("sizeClass", "kbc-icon--sm");
    resource = context.create().resource("/content/icon/static", properties);
    context.request().setResource(resource);
    iconStaticDataSource = context.request().adaptTo(IconStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(iconStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Override
  public void doComponentTypeSetup() {
    // nothing.
  }

  @Test
  public void testGetIconType() {
    assertEquals("svg", iconStaticDataSource.getIconType());
  }

  @Test
  public void testGetIconTypeDefaultsToFont() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/empty", emptyProps);
    context.request().setResource(emptyResource);
    IconStaticDataSource emptyDataSource = context.request().adaptTo(IconStaticDataSource.class);
    assertEquals("font", emptyDataSource.getIconType());
  }

  @Test
  public void testGetIconClass() {
    assertEquals("fa fa-star", iconStaticDataSource.getIconClass());
  }

  @Test
  public void testGetIconClassDefaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/empty2", emptyProps);
    context.request().setResource(emptyResource);
    IconStaticDataSource emptyDataSource = context.request().adaptTo(IconStaticDataSource.class);
    assertEquals("", emptyDataSource.getIconClass());
  }

  @Test
  public void testGetIconPath() {
    assertEquals("/content/dam/icons/star.svg", iconStaticDataSource.getIconPath());
  }

  @Test
  public void testGetAltText() {
    assertEquals("A star icon", iconStaticDataSource.getAltText());
  }

  @Test
  public void testGetHideFromScreenReader() {
    assertTrue(iconStaticDataSource.getHideFromScreenReader());
  }

  @Test
  public void testGetHideFromScreenReaderDefault() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/empty3", emptyProps);
    context.request().setResource(emptyResource);
    IconStaticDataSource emptyDataSource = context.request().adaptTo(IconStaticDataSource.class);
    assertFalse(emptyDataSource.getHideFromScreenReader());
  }

  @Test
  public void testGetHref() {
    assertEquals("https://example.com", iconStaticDataSource.getHref());
  }

  @Test
  public void testGetSizeClass() {
    assertEquals("kbc-icon--sm", iconStaticDataSource.getSizeClass());
  }

  @Test
  public void testGetSizeClassDefaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/empty4", emptyProps);
    context.request().setResource(emptyResource);
    IconStaticDataSource emptyDataSource = context.request().adaptTo(IconStaticDataSource.class);
    assertEquals("", emptyDataSource.getSizeClass());
  }
}
