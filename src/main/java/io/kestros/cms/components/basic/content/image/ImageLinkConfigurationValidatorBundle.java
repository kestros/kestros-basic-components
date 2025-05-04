package io.kestros.cms.components.basic.content.image;

import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.models.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

public class ImageLinkConfigurationValidatorBundle extends ModelValidatorBundle<ImageComponent> {

  public ImageLinkConfigurationValidatorBundle() {
    this.registerValidators();
  }

  @Override
  public void registerValidators() {
    addValidator(getHrefNotConfiguredValidator());
    addValidator(new ImageLinkPathValidatorBundle());
  }

  @Override
  public boolean isAllMustBeTrue() {
    return false;
  }

  @Nonnull
  @Override
  public String getMessage() {
    return "Image link is valid.";
  }

  ModelValidator getHrefNotConfiguredValidator() {
    return new ModelValidator<ImageComponent>() {
      @Override
      public Boolean isValidCheck(ImageComponent model) {
        return StringUtils.isEmpty(model.getHrefPropertyValue());
      }

      @Override
      public String getMessage() {
        return "Image href is empty/not configured.";
      }

      @Override
      public String getDetailedMessage(ImageComponent model) {
        return "";
      }

      @Nonnull
      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.WARNING;
      }
    };
  }

}
