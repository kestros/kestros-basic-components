/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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