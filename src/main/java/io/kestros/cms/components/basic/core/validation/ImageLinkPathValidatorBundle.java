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
