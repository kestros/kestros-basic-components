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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.content.alert.AlertStaticDataSource;
import io.kestros.cms.components.basic.core.content.alert.KestrosAlertImpl;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class BaseSyntheticResourceTest extends BaseSyntheticTest {
  private AlertStaticDataSource alertStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();
  private KestrosAlertImpl alert;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    properties.put("heading", "Test Heading");
    properties.put("text", "Test Text");
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alertStaticDataSource = context.request().adaptTo(AlertStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
        "alertElement");
    assertNotNull(alert.toSyntheticResource(context.resourceResolver(), "/test"));
    assertEquals("/synthetics/test/alertElement",
        alert.toSyntheticResource(context.resourceResolver(), "/test").getPath());
  }

  @Test
  public void testInitWhenResolverIsNull() {
    alertStaticDataSource = mock(AlertStaticDataSource.class);
    resource = mock(Resource.class);
    when(alertStaticDataSource.getResource()).thenReturn(resource);
    Exception exception = null;
    try {
      alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
          "alertElement");
    } catch (ComponentConfigurationException e) {
      exception = e;
    }
    assertNotNull(exception);
    assertEquals("Missing required property", exception.getMessage());
  }

  @Test
  public void testInitWhenParentPathIsNull() {
    alertStaticDataSource = mock(AlertStaticDataSource.class);
    resource = mock(Resource.class);

    when(alertStaticDataSource.getResource()).thenReturn(resource);
    when(alertStaticDataSource.getResourceResolver()).thenReturn(context.resourceResolver());
    Exception exception = null;
    try {
      alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
          "alertElement");
    } catch (ComponentConfigurationException e) {
      exception = e;
    }
    assertNotNull(exception);
    assertEquals("Missing required property", exception.getMessage());
  }

  @Test
  public void testInitWhenVariationsIsNull() {
    alertStaticDataSource = mock(AlertStaticDataSource.class);
    resource = mock(Resource.class);
    when(alertStaticDataSource.getResource()).thenReturn(resource);
    when(alertStaticDataSource.getResourceResolver()).thenReturn(context.resourceResolver());
    when(alertStaticDataSource.getParentPath()).thenReturn("/path");
    Exception exception = null;
    try {
      alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
          "alertElement");
    } catch (ComponentConfigurationException e) {
      exception = e;
    }
    assertNotNull(exception);
    assertEquals("Missing required property", exception.getMessage());
  }

  @Test
  public void testGetId() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
        "alertElement");
    assertNull(alert.getId());
  }

  @Test
  public void testGetResourceResolver() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
        "alertElement");
    assertNotNull(alert.getResourceResolver());

  }

  @Test
  public void testGetParentPath() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
        "alertElement");
    assertEquals("/content/alert/static/heading", alert.getParentPath());
  }

  @Test
  public void testGetResource() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
        "alertElement");
    assertNotNull(alert.getResource());
    assertEquals("/synthetics/content/alert/static/heading/alertElement", alert.getResource().getPath());
  }

  @Test
  public void testGetForcedResourceName() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("test", "test", alertStaticDataSource, "alert",
        "alertElement");
    assertEquals("alertElement", alert.getForcedResourceName());
  }

  @Test
  public void testGetVariations() throws ComponentConfigurationException {

  }

  @Test
  public void testGetLayout() {
  }

  @Test
  public void testGetUiFramework() {
  }

  @Test
  public void testGetComponentVariationRetrievalService() {
  }

  @Test
  public void testGetComponentUiFrameworkViewRetrievalService() {
  }
}