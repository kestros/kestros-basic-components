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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.BaseModelValidationRegistrationService;
import io.kestros.commons.validation.models.ModelValidator;
import io.kestros.commons.validation.services.ModelValidatorRegistrationHandlerService;
import io.kestros.commons.validation.services.ModelValidatorRegistrationService;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Validation service for the {@link TextComponent} component.
 */
@Component(immediate = true,
           service = ModelValidatorRegistrationService.class)
public class TextComponentValidationService extends BaseModelValidationRegistrationService {

  @Reference(cardinality = ReferenceCardinality.OPTIONAL,
             policyOption = ReferencePolicyOption.GREEDY)
  private ModelValidatorRegistrationHandlerService modelValidatorRegistrationHandlerService;

  @Override
  public ModelValidatorRegistrationHandlerService getModelValidatorRegistrationHandlerService() {
    return modelValidatorRegistrationHandlerService;
  }

  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return TextComponent.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    return Collections.singletonList(hasText());
  }

  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  ModelValidator hasText() {
    return new ModelValidator<TextComponent>() {
      @Override
      public Boolean isValidCheck() {
        return StringUtils.isNotEmpty(getModel().getText());
      }

      @Override
      public String getMessage() {
        return "Text is configured.";
      }

      @Override
      public String getDetailedMessage() {
        return "'text' property must be configured.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }


}
