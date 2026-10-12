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

package io.kestros.cms.components.basic.core.content.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class AlertStaticDataSourceTest extends BaseDataSourceTest {


  private AlertStaticDataSource alertStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();


  @Override
  public void doComponentSetup() {
    properties.put("heading", "Test Heading");
    properties.put("text", "Test Text");
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alertStaticDataSource = context.request().adaptTo(AlertStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(alertStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }


  @Test
  public void testGetHeading() {
    String heading = alertStaticDataSource.getHeading();
    assertEquals("Test Heading", heading);
  }

  @Test
  public void testGetText() {
    String text = alertStaticDataSource.getText();
    assertEquals("Test Text", text);
  }

  @Override
  public void doComponentTypeSetup() {
    // nothing.
  }
}
