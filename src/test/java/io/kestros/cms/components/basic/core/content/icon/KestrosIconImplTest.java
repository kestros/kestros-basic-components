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

public class KestrosIconImplTest extends BaseDataSourceTest {

  private KestrosIconImpl kestrosIconImpl;
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
    resource = context.create().resource("/content/icon/impl", properties);
    kestrosIconImpl = resource.adaptTo(KestrosIconImpl.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(kestrosIconImpl);
  }

  @Override
  public void doComponentTypeSetup() {
    // nothing.
  }

  @Test
  public void testGetIconType_returnsSetValue() {
    assertNotNull(kestrosIconImpl);
    assertEquals("svg", kestrosIconImpl.getIconType());
  }

  @Test
  public void testGetIconType_defaultsToFont() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertEquals("font", emptyImpl.getIconType());
  }

  @Test
  public void testGetIconClass_returnsSetValue() {
    assertNotNull(kestrosIconImpl);
    assertEquals("fa fa-star", kestrosIconImpl.getIconClass());
  }

  @Test
  public void testGetIconClass_defaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty2", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertEquals("", emptyImpl.getIconClass());
  }

  @Test
  public void testGetIconPath_returnsSetValue() {
    assertNotNull(kestrosIconImpl);
    assertEquals("/content/dam/icons/star.svg", kestrosIconImpl.getIconPath());
  }

  @Test
  public void testGetIconPath_defaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty3", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertEquals("", emptyImpl.getIconPath());
  }

  @Test
  public void testGetAltText_returnsSetValue() {
    assertNotNull(kestrosIconImpl);
    assertEquals("A star icon", kestrosIconImpl.getAltText());
  }

  @Test
  public void testGetAltText_defaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty4", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertEquals("", emptyImpl.getAltText());
  }

  @Test
  public void testGetHideFromScreenReader_returnsTrue() {
    assertNotNull(kestrosIconImpl);
    assertTrue(kestrosIconImpl.getHideFromScreenReader());
  }

  @Test
  public void testGetHideFromScreenReader_defaultsFalse() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty5", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertFalse(emptyImpl.getHideFromScreenReader());
  }

  @Test
  public void testGetHref_returnsSetValue() {
    assertNotNull(kestrosIconImpl);
    assertEquals("https://example.com", kestrosIconImpl.getHref());
  }

  @Test
  public void testGetHref_defaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty6", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertEquals("", emptyImpl.getHref());
  }

  @Test
  public void testGetSizeClass_returnsSetValue() {
    assertNotNull(kestrosIconImpl);
    assertEquals("kbc-icon--sm", kestrosIconImpl.getSizeClass());
  }

  @Test
  public void testGetSizeClass_defaultsToEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource("/content/icon/impl-empty7", emptyProps);
    KestrosIconImpl emptyImpl = emptyResource.adaptTo(KestrosIconImpl.class);
    assertNotNull(emptyImpl);
    assertEquals("", emptyImpl.getIconPath());
  }
}
