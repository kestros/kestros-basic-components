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

public class ImageLinkPathValidatorBundle {
//        extends ModelValidatorBundle<ImageComponent> {
//
//  public ImageLinkPathValidatorBundle() {
//    this.registerValidators();
//  }
//
//  @Override
//  public void registerValidators() {
//    addValidator(getLinkIsResourceValidator());
//    addValidator(getLinkIsExternalValidator());
//  }
//
//  @Override
//  public boolean isAllMustBeTrue() {
//    return false;
//  }
//
//  @Override
//  public String getMessage() {
//    return "Image link path is valid.";
//  }
//
//
//  ModelValidator getLinkIsResourceValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        if (!model.isLinkExternal()) {
//          Resource linkResource = model.getLinkResource();
//          if (linkResource != null) {
//            return true;
//          }
//        }
//        return false;
//      }
//
//      @Override
//      public String getMessage() {
//        return "Image link is a valid Kestros resource path.";
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
//
//  ModelValidator getLinkIsExternalValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return model.isLinkExternal();
//      }
//
//      @Override
//      public String getMessage() {
//        return "Image link is an external path.";
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
//
//  }
}
