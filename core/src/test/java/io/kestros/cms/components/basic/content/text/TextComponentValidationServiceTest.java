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