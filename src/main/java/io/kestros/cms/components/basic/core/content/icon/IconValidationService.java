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

/**
 * Validation service for the {@link KestrosIconImpl} component.
 * Validation logic is defined below but OSGi registration is deferred pending
 * BaseSlingModel support on BaseSyntheticResource subclasses.
 *
 * Validators to enable when wired:
 * - hasIconType() — ERROR if iconType is blank
 * - hasIconClassOrPath() — WARNING if both iconClass and iconPath are blank
 * - hasAltTextForSvgOrImage() — WARNING if iconType is svg/image and altText is blank
 */
//@Component(immediate = true,
//    service = ModelValidatorRegistrationService.class)
public class IconValidationService {
//  extends BaseModelValidationRegistrationService {
//
//  @Reference(cardinality = ReferenceCardinality.OPTIONAL,
//      policyOption = ReferencePolicyOption.GREEDY)
//  private ModelValidatorRegistrationHandlerService modelValidatorRegistrationHandlerService;
//
//  @Override
//  public ModelValidatorRegistrationHandlerService getModelValidatorRegistrationHandlerService() {
//    return modelValidatorRegistrationHandlerService;
//  }
//
//  @Override
//  public Class<? extends BaseSlingModel> getModelType() {
//    return KestrosIconImpl.class;
//  }
//
//  @Override
//  public List<ModelValidator> getModelValidators() {
//    return Arrays.asList(hasIconType(), hasIconClassOrPath(), hasAltTextForSvgOrImage());
//  }
//
//  ModelValidator hasIconType() {
//    return new ModelValidator<KestrosIconImpl>() {
//      @Override
//      public Boolean isValidCheck(@Nonnull KestrosIconImpl model) {
//        return StringUtils.isNotBlank(model.getIconType());
//      }
//      @Override
//      public String getMessage() { return "Icon type is configured."; }
//      @Override
//      public String getDetailedMessage(@Nonnull KestrosIconImpl model) {
//        return "'iconType' property must be configured. Expected: 'font', 'svg', or 'image'.";
//      }
//      @Override
//      public ModelValidationMessageType getType() { return ModelValidationMessageType.ERROR; }
//    };
//  }
//
//  ModelValidator hasIconClassOrPath() {
//    return new ModelValidator<KestrosIconImpl>() {
//      @Override
//      public Boolean isValidCheck(@Nonnull KestrosIconImpl model) {
//        return StringUtils.isNotBlank(model.getIconClass())
//            || StringUtils.isNotBlank(model.getIconPath());
//      }
//      @Override
//      public String getMessage() { return "Icon class or path is configured."; }
//      @Override
//      public String getDetailedMessage(@Nonnull KestrosIconImpl model) {
//        return "At least one of 'iconClass' or 'iconPath' must be configured.";
//      }
//      @Override
//      public ModelValidationMessageType getType() { return ModelValidationMessageType.WARNING; }
//    };
//  }
//
//  ModelValidator hasAltTextForSvgOrImage() {
//    return new ModelValidator<KestrosIconImpl>() {
//      @Override
//      public Boolean isValidCheck(@Nonnull KestrosIconImpl model) {
//        String iconType = model.getIconType();
//        if ("svg".equals(iconType) || "image".equals(iconType)) {
//          return StringUtils.isNotBlank(model.getAltText());
//        }
//        return true;
//      }
//      @Override
//      public String getMessage() { return "Alt text is configured for SVG or image icons."; }
//      @Override
//      public String getDetailedMessage(@Nonnull KestrosIconImpl model) {
//        return "'altText' must be configured when iconType is 'svg' or 'image'.";
//      }
//      @Override
//      public ModelValidationMessageType getType() { return ModelValidationMessageType.WARNING; }
//    };
//  }
}
