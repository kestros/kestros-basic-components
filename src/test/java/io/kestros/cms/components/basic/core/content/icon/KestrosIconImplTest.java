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

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosIconImplTest extends BaseSyntheticTest {

  private KestrosIconImpl icon;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    icon = new KestrosIconImpl("svg", "fa fa-star", "/content/dam/icons/star.svg",
        "A star icon", true, "https://example.com", "kbc-icon--sm",
        dataSource, "icon", "icon-resource");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = icon.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
  }

  @Test
  public void testGetIconType() {
    assertEquals("svg", icon.getIconType());
  }

  @Test
  public void testGetIconTypeDefaultsToFont() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-defaults");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl defaultIcon = new KestrosIconImpl(null, null, null, null, false,
        null, null, dataSource, "icon", "icon-defaults");
    assertEquals("font", defaultIcon.getIconType());
  }

  @Test
  public void testGetIconClass() {
    assertEquals("fa fa-star", icon.getIconClass());
  }

  @Test
  public void testGetIconClassDefaultsToEmpty() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-empty-class");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl emptyIcon = new KestrosIconImpl("font", null, null, null, false,
        null, null, dataSource, "icon", "icon-empty-class");
    assertEquals("", emptyIcon.getIconClass());
  }

  @Test
  public void testGetIconPath() {
    assertEquals("/content/dam/icons/star.svg", icon.getIconPath());
  }

  @Test
  public void testGetIconPathDefaultsToEmpty() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-empty-path");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl emptyIcon = new KestrosIconImpl("svg", null, null, null, false,
        null, null, dataSource, "icon", "icon-empty-path");
    assertEquals("", emptyIcon.getIconPath());
  }

  @Test
  public void testGetAltText() {
    assertEquals("A star icon", icon.getAltText());
  }

  @Test
  public void testGetAltTextDefaultsToEmpty() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-empty-alt");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl emptyIcon = new KestrosIconImpl("svg", null, null, null, false,
        null, null, dataSource, "icon", "icon-empty-alt");
    assertEquals("", emptyIcon.getAltText());
  }

  @Test
  public void testGetHideFromScreenReaderTrue() {
    assertTrue(icon.getHideFromScreenReader());
  }

  @Test
  public void testGetHideFromScreenReaderDefaultsFalse() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-no-hide");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl defaultIcon = new KestrosIconImpl("font", null, null, null, false,
        null, null, dataSource, "icon", "icon-no-hide");
    assertFalse(defaultIcon.getHideFromScreenReader());
  }

  @Test
  public void testGetHref() {
    assertEquals("https://example.com", icon.getHref());
  }

  @Test
  public void testGetHrefDefaultsToEmpty() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-empty-href");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl emptyIcon = new KestrosIconImpl("image", null, null, null, false,
        null, null, dataSource, "icon", "icon-empty-href");
    assertEquals("", emptyIcon.getHref());
  }

  @Test
  public void testGetSizeClass() {
    assertEquals("kbc-icon--sm", icon.getSizeClass());
  }

  @Test
  public void testGetSizeClassDefaultsToEmpty() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent-empty-size");
    context.currentResource(resource);
    IconStaticDataSource dataSource = context.request().adaptTo(IconStaticDataSource.class);

    KestrosIconImpl emptyIcon = new KestrosIconImpl("font", null, null, null, false,
        null, null, dataSource, "icon", "icon-empty-size");
    assertEquals("", emptyIcon.getSizeClass());
  }
}
