package io.kestros.cms.components.basic.core.content.alert.validation;

/**
 * Validators applied to the alert message component.
 */
@Deprecated
public class AlertMessageValidatorBundle {
//        extends ModelValidatorBundle<AlertComponent> {
//
//  public AlertMessageValidatorBundle() {
//    this.registerValidators();
//  }
//
//  @Override
//  public void registerValidators() {
//    addValidator(getHeadingValidator());
//    addValidator(getMessageValidator());
//  }
//
//  @Override
//  public boolean isAllMustBeTrue() {
//    return false;
//  }
//
//  @Nonnull
//  @Override
//  public String getMessage() {
//    return "Heading or message is configured";
//  }
//
//  ModelValidator<AlertComponent> getHeadingValidator() {
//    return new ModelValidator<AlertComponent>() {
//      @Override
//      public Boolean isValidCheck(AlertComponent model) {
//        return StringUtils.isNotBlank(model.getHeading());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Heading is configured.";
//      }
//
//      @Override
//      public String getDetailedMessage(AlertComponent model) {
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
//  ModelValidator<AlertComponent> getMessageValidator() {
//    return new ModelValidator<AlertComponent>() {
//      @Override
//      public Boolean isValidCheck(AlertComponent model) {
//        return StringUtils.isNotEmpty(model.getText());
//      }
//
//      @Override
//      public String getMessage() {
//        return "Text is configured.";
//      }
//
//      @Override
//      public String getDetailedMessage(AlertComponent model) {
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
