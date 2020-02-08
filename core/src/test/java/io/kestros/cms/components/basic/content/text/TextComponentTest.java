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

package io.kestros.cms.components.basic.content.text;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TextComponentTest {

  @Rule
  public SlingContext context = new SlingContext();

  private TextComponent textComponent;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();


  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
  }

  @Test
  public void testGetText() {
    properties.put("text", "My Text.");
    resource = context.create().resource("/text-component", properties);

    textComponent = resource.adaptTo(TextComponent.class);

    assertEquals("My Text.", textComponent.getText());
  }

  @Test
  public void testGetTextWhenMissing() {
    resource = context.create().resource("/text-component", properties);

    textComponent = resource.adaptTo(TextComponent.class);

    assertEquals("", textComponent.getText());
  }
}