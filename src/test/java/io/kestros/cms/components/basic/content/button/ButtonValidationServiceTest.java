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

package io.kestros.cms.components.basic.content.button;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.kestros.commons.validation.models.ModelValidator;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ButtonValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private ButtonValidationService buttonValidationService;
  private Button button;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    buttonValidationService = new ButtonValidationService();
  }

  @Test
  public void testGetModelType() {
    assertEquals(Button.class, buttonValidationService.getModelType());
  }

  @Test
  public void testGetModelValidators() {
    assertEquals(2, buttonValidationService.getModelValidators().size());
    assertEquals("Text is configured.",
        buttonValidationService.getModelValidators().get(0).getMessage());
    assertEquals("Link has been configured.",
        buttonValidationService.getModelValidators().get(1).getMessage());
  }

  @Test
  public void testHasLink() {
    properties.put("link", "button-link");
    resource = context.create().resource("/button", properties);
    button = resource.adaptTo(Button.class);
    ModelValidator validator = buttonValidationService.hasLink();
    validator.setModel(button);
    assertTrue(validator.isValidCheck());
  }
}