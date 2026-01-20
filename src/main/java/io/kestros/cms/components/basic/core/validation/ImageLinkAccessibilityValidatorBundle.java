package io.kestros.cms.components.basic.core.validation;

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
