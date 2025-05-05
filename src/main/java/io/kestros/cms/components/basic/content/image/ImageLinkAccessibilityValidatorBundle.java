package io.kestros.cms.components.basic.content.image;

import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.models.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class ImageLinkAccessibilityValidatorBundle extends ModelValidatorBundle<ImageComponent> {

  public ImageLinkAccessibilityValidatorBundle() {
    this.registerValidators();
  }

  @Override
  public void registerValidators() {
    addValidator(getHrefNotConfiguredValidator());
    addValidator(new ImageLinkAccessibilityPropertiesValidatorBundle());
  }

  @Override
  public boolean isAllMustBeTrue() {
    return false;
  }

  @Override
  public String getMessage() {
    return "Image link accessibility properties are configured.";
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
