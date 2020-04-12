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

package io.kestros.cms.components.basic.content.code;

import io.kestros.cms.components.basic.content.text.TextComponent;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * Validation service for the {@link CodeComponent} component.
 */
public class CodeComponentValidationService extends ModelValidationService {

  @Override
  public TextComponent getModel() {
    return (CodeComponent) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(hasCode());
  }

  @Override
  public void registerDetailedValidators() {
    return;
  }

  ModelValidator hasCode() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return StringUtils.isNotEmpty(getModel().getText());
      }

      @Override
      public String getMessage() {
        return "Code is configured.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }

}
