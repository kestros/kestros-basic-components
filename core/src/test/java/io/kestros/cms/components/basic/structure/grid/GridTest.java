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

package io.kestros.cms.components.basic.structure.grid;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GridTest {


  @Rule
  public SlingContext context = new SlingContext();

  private Grid grid;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();
  private Map<String, Object> contentAreaProperties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    contentAreaProperties.put("sling:resourceType", "kestros/commons/components/content-area");
  }

  @Test
  public void testGetNumberOfColumns() {
    properties.put("columns", 2);
    resource = context.create().resource("/grid", properties);
    grid = resource.adaptTo(Grid.class);

    assertEquals(2, grid.getNumberOfColumns());
  }

  @Test
  public void testGetNumberOfColumnsWhenNumberNotDefined() {
    resource = context.create().resource("/grid", properties);
    grid = resource.adaptTo(Grid.class);

    assertEquals(3, grid.getNumberOfColumns());
  }

  @Test
  public void testGetColumns() {
    properties.put("columns", 2);
    resource = context.create().resource("/grid", properties);
    context.create().resource("/grid/column-1", contentAreaProperties);
    context.create().resource("/grid/column-2", contentAreaProperties);
    grid = resource.adaptTo(Grid.class);

    assertEquals(2, grid.getColumns().size());
  }

  @Test
  public void testGetColumnsWhenColumnNotFound() {
    properties.put("columns", 2);
    resource = context.create().resource("/grid", properties);
    context.create().resource("/grid/column-1", contentAreaProperties);
    grid = resource.adaptTo(Grid.class);

    assertEquals(1, grid.getColumns().size());
  }
}