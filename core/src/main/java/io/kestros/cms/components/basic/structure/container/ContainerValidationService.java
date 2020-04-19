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

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;

/**
 * Validation Service for the {@link Container} component.
 */
public class ContainerValidationService extends ModelValidationService {

  @Override
  public Container getModel() {
    return (Container) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(hasChild());
  }

  @Override
  public void registerDetailedValidators() {
    return;
  }

  ModelValidator hasChild() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return !getModel().getChildren().isEmpty();
      }

      @Override
      public String getMessage() {
        return "Has at least one child component.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.WARNING;
      }
    };
  }
}
