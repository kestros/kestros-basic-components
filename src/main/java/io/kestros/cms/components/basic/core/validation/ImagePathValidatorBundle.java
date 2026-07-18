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

public class ImagePathValidatorBundle {
//  extends ModelValidatorBundle<ImageComponent>
//} {
//
//  public ImagePathValidatorBundle() {
//    this.registerValidators();
//  }
//
//  @Override
//  public void registerValidators() {
//    addValidator(getImageIsAssetValidator());
//    addValidator(getImageIsExternalValidator());
//  }
//
//  @Override
//  public boolean isAllMustBeTrue() {
//    return false;
//  }
//
//  @Override
//  public String getMessage() {
//    return "Image path is valid.";
//  }
//
//
//  ModelValidator getImageIsAssetValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        if (StringUtils.isNotEmpty(model.getImagePathPropertyValue()) && model.isAssetResource
//        ()) {
//          try {
//            model.getAsset();
//            return true;
//          } catch (AssetRetrievalException e) {
//            return false;
//          }
//        }
//        return false;
//      }
//
//      @Override
//      public String getMessage() {
//        return "Image is a Kestros asset path.";
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
//  ModelValidator getImageIsExternalValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return !model.isAssetResource();
//      }
//
//      @Override
//      public String getMessage() {
//        return "Image is an external path.";
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
