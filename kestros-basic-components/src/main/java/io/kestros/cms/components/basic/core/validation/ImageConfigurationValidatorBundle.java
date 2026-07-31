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
 * Placeholder for the image configuration validation. The implementation is commented out and
 * nothing
 * is registered, so no validation runs for it today.
 */
public class ImageConfigurationValidatorBundle {
//        extends ModelValidatorBundle<ImageComponent> {
//
//  public ImageConfigurationValidatorBundle() {
//    this.registerValidators();
//  }
//
//  @Override
//  public void registerValidators() {
//    addValidator(getImagePathConfigurationValidator());
//    addValidator(new ImagePathValidatorBundle());
//  }
//
//  @Override
//  public boolean isAllMustBeTrue() {
//    return true;
//  }
//
//  @Nonnull
//  @Override
//  public String getMessage() {
//    return "Image configuration is valid.";
//  }
//
//  ModelValidator getImagePathConfigurationValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return StringUtils.isNotEmpty(model.getImagePathPropertyValue());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Image path is configured.";
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
//        return ModelValidationMessageType.ERROR;
//      }
//    };
//  }

}
