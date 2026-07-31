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

package io.kestros.cms.components.basic.core.validation;

/**
 * Placeholder for the image link accessibility validation. The implementation is commented out and
 * nothing
 * is registered, so no validation runs for it today.
 */
public class ImageLinkAccessibilityValidatorBundle {
//        extends ModelValidatorBundle<ImageComponent> {
//
//  public ImageLinkAccessibilityValidatorBundle() {
//    this.registerValidators();
//  }
//
//  @Override
//  public void registerValidators() {
//    addValidator(getHrefNotConfiguredValidator());
//    addValidator(new ImageLinkAccessibilityPropertiesValidatorBundle());
//  }
//
//  @Override
//  public boolean isAllMustBeTrue() {
//    return false;
//  }
//
//  @Override
//  public String getMessage() {
//    return "Image link accessibility properties are configured.";
//  }
//
//  ModelValidator getHrefNotConfiguredValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return StringUtils.isEmpty(model.getHrefPropertyValue());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Image href is empty/not configured.";
//      }
//
//      @Override
//      public String getDetailedMessage(ImageComponent model) {
//        return "";
//      }
//
//      @Nonnull
//      @Override
//      public ModelValidationMessageType getType() {
//        return ModelValidationMessageType.WARNING;
//      }
//    };
//  }
}
