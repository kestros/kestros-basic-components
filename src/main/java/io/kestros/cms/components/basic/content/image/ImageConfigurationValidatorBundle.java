package io.kestros.cms.components.basic.content.image;

import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.models.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

public class ImageConfigurationValidatorBundle extends ModelValidatorBundle<ImageComponent> {

  public ImageConfigurationValidatorBundle() {
    this.registerValidators();
  }

  @Override
  public void registerValidators() {
    addValidator(getImagePathConfigurationValidator());
    addValidator(new ImagePathValidatorBundle());
  }

  @Override
  public boolean isAllMustBeTrue() {
    return true;
  }

  @Nonnull
  @Override
  public String getMessage() {
    return "Image configuration is valid.";
  }

  ModelValidator getImagePathConfigurationValidator() {
    return new ModelValidator<ImageComponent>() {
      @Override
      public Boolean isValidCheck(ImageComponent model) {
        return StringUtils.isNotEmpty(model.getImagePathPropertyValue());
      }

      @Override
      public String getMessage() {
        return "Image path is configured.";
      }

      @Override
      public String getDetailedMessage(ImageComponent model) {
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
