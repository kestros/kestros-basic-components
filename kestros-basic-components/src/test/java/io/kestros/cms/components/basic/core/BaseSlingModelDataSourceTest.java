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

package io.kestros.cms.components.basic.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.content.alert.AlertStaticDataSource;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class BaseSlingModelDataSourceTest extends BaseDataSourceTest {

  private AlertStaticDataSource alert;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    // test page already set up
  }

  @Override
  public void testToSyntheticResource() {

  }

  @Test
  public void testGetId() {
    properties.put("id", "ID");
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertEquals("ID", alert.getId());
  }

  @Test
  public void testIsSynthetic() {
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertFalse(alert.isSynthetic());
  }

  @Test
  public void testGetResource() {
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getResource());
    assertEquals("/content/alert/static/heading", alert.getResource().getPath());
  }

  @Test
  public void testGetCurrentOrContainingPage() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getCurrentOrContainingPage());
    assertEquals("/content/sites/test", alert.getCurrentOrContainingPage().getPath());
  }

  @Test
  public void testGetCurrentOrContainingPageWhenRequestIsNull() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    alert = resource.adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getCurrentOrContainingPage());
    assertEquals("/content/sites/test", alert.getCurrentOrContainingPage().getPath());
  }

  @Test
  public void testGetResourceResolver() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getResourceResolver());
  }

  @Test
  public void testGetParentPath() {
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertEquals("/content/alert/static", alert.getParentPath());
  }

  @Test
  public void testGetVariations() {

  }

  @Test
  public void testGetLayout() {
  }

  @Test
  public void testGetForcedResourceName() {
  }

  @Test
  public void testGetUiFramework() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetUiFrameworkWhenNoRequest() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    alert = resource.adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetComponentVariationRetrievalService() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getComponentVariationRetrievalService());
  }

  @Test
  public void testGetComponentUiFrameworkViewRetrievalService() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getComponentUiFrameworkViewRetrievalService());
  }
}
