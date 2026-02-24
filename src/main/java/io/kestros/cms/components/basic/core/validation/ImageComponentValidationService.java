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


/// **
// * Validation Service for the {@link ImageComponent} Component.
// */
//@Component(immediate = true,
//    service = ModelValidatorRegistrationService.class)
public class ImageComponentValidationService {
//        extends BaseModelValidationRegistrationService {
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
//    return ImageComponent.class;
//  }
//
//  @Override
//  public List<ModelValidator> getModelValidators() {
//    List<ModelValidator> modelValidators = new ArrayList<>();
//    modelValidators.add(new ImageConfigurationValidatorBundle());
//    modelValidators.add(getAltTextValidator());
//    modelValidators.add(new ImageLinkConfigurationValidatorBundle());
//    modelValidators.add(new ImageLinkAccessibilityValidatorBundle());
//    return modelValidators;
//  }
//
//
//  ModelValidator getAltTextValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return StringUtils.isNoneBlank(model.getAltText());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Alt text is configured.";
//      }
//
//      @Nonnull
//      @Override
//      public String getDetailedMessage(@Nonnull ImageComponent model) {
//        return "Alt text is required for accessibility.";
//      }
//
//      @Nonnull
//      @Override
//      public ModelValidationMessageType getType() {
//        return ModelValidationMessageType.WARNING;
//      }
//
//    };
//  }

}
