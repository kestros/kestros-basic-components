package io.kestros.cms.components.basic.core.validation;

/**
 * Placeholder for the image link accessibility properties validation. The implementation is
 * commented out and nothing
 * is registered, so no validation runs for it today.
 */
public class ImageLinkAccessibilityPropertiesValidatorBundle {
//        extends ModelValidatorBundle<ImageComponent> {
//
//  public ImageLinkAccessibilityPropertiesValidatorBundle() {
//    this.registerValidators();
//  }
//
//  @Override
//  public void registerValidators() {
//    addValidator(getAriaLabelConfiguredValidator());
//    addValidator(getLinkTitleConfiguredValidator());
//  }
//
//  @Override
//  public boolean isAllMustBeTrue() {
//    return true;
//  }
//
//  @Override
//  public String getMessage() {
//    return "Image link accessibility properties are configured.";
//  }
//
//
//  ModelValidator getAriaLabelConfiguredValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return StringUtils.isNotEmpty(model.getAriaLabel());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Link Aria label is configured.";
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
//  ModelValidator getLinkTitleConfiguredValidator() {
//    return new ModelValidator<ImageComponent>() {
//      @Override
//      public Boolean isValidCheck(ImageComponent model) {
//        return StringUtils.isNotEmpty(model.getAnchorTitle());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Link title is configured.";
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
