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

package io.kestros.cms.components.basic.structure.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.kestros.commons.validation.models.ModelValidator;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ContainerValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private ContainerValidationService containerValidationService;
  private Container container;
  private Resource resource;


  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros.cms");
    containerValidationService = new ContainerValidationService();
  }

  @Test
  public void testGetModelType() {
    assertEquals(Container.class, containerValidationService.getModelType());
  }

  @Test
  public void testGetModelValidators() {
    assertEquals(1, containerValidationService.getModelValidators().size());
  }

  @Test
  public void testHasChild() {
    resource = context.create().resource("/resource");
    context.create().resource("/resource/child");
    container = resource.adaptTo(Container.class);
    ModelValidator validator = containerValidationService.hasChild();
    validator.setModel(container);
    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testHasChildWhenHasNoChildren() {
    resource = context.create().resource("/resource");
    container = resource.adaptTo(Container.class);
    ModelValidator validator = containerValidationService.hasChild();
    validator.setModel(container);
    assertFalse(validator.isValidCheck());
  }
}