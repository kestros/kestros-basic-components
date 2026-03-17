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

package io.kestros.cms.components.basic.core.content.icon;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.services.BaseModelValidationRegistrationService;
import io.kestros.commons.validation.api.services.ModelValidatorRegistrationHandlerService;
import io.kestros.commons.validation.api.services.ModelValidatorRegistrationService;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Validation service for the {@link KestrosIconImpl} component.
 * Registers three validators:
 * <ul>
 *   <li>{@link #hasIconType()} — ERROR if iconType is blank</li>
 *   <li>{@link #hasIconClassOrPath()} — WARNING if both iconClass and iconPath are blank</li>
 *   <li>{@link #hasAltTextForSvgOrImage()} — WARNING if iconType is svg/image and altText is blank</li>
 * </ul>
 */
@Component(immediate = true,
    service = ModelValidatorRegistrationService.class)
public class IconValidationService extends BaseModelValidationRegistrationService {

  @Reference(cardinality = ReferenceCardinality.OPTIONAL,
      policyOption = ReferencePolicyOption.GREEDY)
  private ModelValidatorRegistrationHandlerService modelValidatorRegistrationHandlerService;

  @Override
  @Nullable
  public ModelValidatorRegistrationHandlerService getModelValidatorRegistrationHandlerService() {
    return modelValidatorRegistrationHandlerService;
  }

  @Nonnull
  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return KestrosIconImpl.class;
  }

  @Nonnull
  @Override
  public List<ModelValidator> getModelValidators() {
    return Arrays.asList(hasIconType(), hasIconClassOrPath(), hasAltTextForSvgOrImage());
  }

  /**
   * Validates that the iconType property is configured.
   *
   * @return ModelValidator that is an ERROR if iconType is blank.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  ModelValidator hasIconType() {
    return new ModelValidator<KestrosIconImpl>() {
      @Nonnull
      @Override
      public Boolean isValidCheck(@Nonnull KestrosIconImpl model) {
        return StringUtils.isNotBlank(model.getIconType());
      }

      @Nonnull
      @Override
      public String getMessage() {
        return "Icon type is configured.";
      }

      @Nonnull
      @Override
      public String getDetailedMessage(@Nonnull KestrosIconImpl model) {
        return "'iconType' property must be configured. Expected: 'font', 'svg', or 'image'.";
      }

      @Nonnull
      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }

  /**
   * Validates that at least one of iconClass or iconPath is configured.
   *
   * @return ModelValidator that is a WARNING if both iconClass and iconPath are blank.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  ModelValidator hasIconClassOrPath() {
    return new ModelValidator<KestrosIconImpl>() {
      @Nonnull
      @Override
      public Boolean isValidCheck(@Nonnull KestrosIconImpl model) {
        return StringUtils.isNotBlank(model.getIconClass())
            || StringUtils.isNotBlank(model.getIconPath());
      }

      @Nonnull
      @Override
      public String getMessage() {
        return "Icon class or path is configured.";
      }

      @Nonnull
      @Override
      public String getDetailedMessage(@Nonnull KestrosIconImpl model) {
        return "At least one of 'iconClass' or 'iconPath' must be configured. "
            + "For font icons, set 'iconClass'. For svg/image icons, set 'iconPath'.";
      }

      @Nonnull
      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.WARNING;
      }
    };
  }

  /**
   * Validates that alt text is provided when using SVG or image rendering mode.
   *
   * @return ModelValidator that is a WARNING if iconType is svg/image and altText is blank.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  ModelValidator hasAltTextForSvgOrImage() {
    return new ModelValidator<KestrosIconImpl>() {
      @Nonnull
      @Override
      public Boolean isValidCheck(@Nonnull KestrosIconImpl model) {
        String iconType = model.getIconType();
        if ("svg".equals(iconType) || "image".equals(iconType)) {
          return StringUtils.isNotBlank(model.getAltText());
        }
        return true;
      }

      @Nonnull
      @Override
      public String getMessage() {
        return "Alt text is configured for SVG or image icons.";
      }

      @Nonnull
      @Override
      public String getDetailedMessage(@Nonnull KestrosIconImpl model) {
        return "'altText' must be configured when iconType is 'svg' or 'image' "
            + "for accessibility compliance.";
      }

      @Nonnull
      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.WARNING;
      }
    };
  }
}
