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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TextComponentValidationServiceTest {


  @Rule
  public SlingContext context = new SlingContext();

  private TextComponentValidationService textComponentValidationService;
  private TextComponent textComponent;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();


  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    textComponentValidationService = spy(new TextComponentValidationService());
  }

  @Test
  public void testGetModel() {
    textComponent = new TextComponent();
    doReturn(textComponent).when(textComponentValidationService).getGenericModel();

    assertEquals(textComponent, textComponentValidationService.getModel());
  }

  @Test
  public void testRegisterBasicValidators() {
    textComponent = new TextComponent();
    doReturn(textComponent).when(textComponentValidationService).getGenericModel();

    textComponentValidationService.registerBasicValidators();

    assertEquals(1, textComponentValidationService.getBasicValidators().size());
  }

  @Test
  public void testRegisterDetailedValidators() {
    textComponent = new TextComponent();
    doReturn(textComponent).when(textComponentValidationService).getGenericModel();

    textComponentValidationService.registerDetailedValidators();

    assertEquals(0, textComponentValidationService.getDetailedValidators().size());
  }

  @Test
  public void testHasText() {
    properties.put("text", "text.");
    resource = context.create().resource("/text-component", properties);

    textComponent = resource.adaptTo(TextComponent.class);

    doReturn(textComponent).when(textComponentValidationService).getGenericModel();

    assertTrue(textComponentValidationService.hasText().isValid());
  }

  @Test
  public void testHasTextWhenTextIsEmpty() {
    resource = context.create().resource("/text-component", properties);

    textComponent = resource.adaptTo(TextComponent.class);

    doReturn(textComponent).when(textComponentValidationService).getGenericModel();

    assertFalse(textComponentValidationService.hasText().isValid());
    assertEquals("Text is configured.", textComponentValidationService.hasText().getMessage());
    assertEquals(ModelValidationMessageType.ERROR,
        textComponentValidationService.hasText().getType());
  }
}