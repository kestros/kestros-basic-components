package io.kestros.cms.components.basic.content.alert;

import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.models.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

public class AlertMessageValidatorBundle extends ModelValidatorBundle<AlertComponent> {

  public AlertMessageValidatorBundle() {
    this.registerValidators();
  }

  @Override
  public void registerValidators() {
    addValidator(getHeadingValidator());
    addValidator(getMessageValidator());
  }

  @Override
  public boolean isAllMustBeTrue() {
    return false;
  }

  @Nonnull
  @Override
  public String getMessage() {
    return "Heading or message is configured";
  }

  ModelValidator<AlertComponent> getHeadingValidator() {
    return new ModelValidator<AlertComponent>() {
      @Override
      public Boolean isValidCheck(AlertComponent model) {
        return StringUtils.isNotBlank(model.getHeading());
      }

      @Override
      public String getMessage() {
        return "Heading is configured.";
      }

      @Override
      public String getDetailedMessage(AlertComponent model) {
        return "";
      }

      @Nonnull
      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }

  ModelValidator<AlertComponent> getMessageValidator() {
    return new ModelValidator<AlertComponent>() {
      @Override
      public Boolean isValidCheck(AlertComponent model) {
        return StringUtils.isNotEmpty(model.getText());
      }

      @Override
      public String getMessage() {
        return "Text is configured.";
      }

      @Override
      public String getDetailedMessage(AlertComponent model) {
        return "";
      }

      @Nonnull
      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }
}
