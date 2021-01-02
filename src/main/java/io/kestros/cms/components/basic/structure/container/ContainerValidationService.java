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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import io.kestros.commons.validation.services.ModelValidatorRegistrationService;
import java.util.Collections;
import java.util.List;
import org.osgi.service.component.annotations.Component;

/**
 * Validation Service for the {@link Container} component.
 */
@Component(immediate = true,
           service = ModelValidatorRegistrationService.class)
public class ContainerValidationService implements ModelValidatorRegistrationService {


  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return Container.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    return Collections.singletonList(hasChild());
  }

  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  ModelValidator hasChild() {
    return new ModelValidator<Container>() {
      @Override
      public Boolean isValidCheck() {
        return !getModel().getChildren().isEmpty();
      }

      @Override
      public String getMessage() {
        return "Has at least one child component.";
      }

      @Override
      public String getDetailedMessage() {
        return "";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.WARNING;
      }
    };
  }

}
