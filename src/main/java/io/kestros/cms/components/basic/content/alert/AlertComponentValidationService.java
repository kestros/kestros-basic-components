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

package io.kestros.cms.components.basic.content.alert;

import io.kestros.cms.components.basic.content.text.TextComponentValidationService;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.services.ModelValidatorRegistrationService;
import org.osgi.service.component.annotations.Component;

/**
 * Validation Service for the {@link AlertComponent} Component.
 */
@Component(immediate = true,
           service = ModelValidatorRegistrationService.class)
public class AlertComponentValidationService extends TextComponentValidationService {

  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return AlertComponent.class;
  }

}
