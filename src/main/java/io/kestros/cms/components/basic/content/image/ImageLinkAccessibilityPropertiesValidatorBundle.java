package io.kestros.cms.components.basic.content.image;

import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.models.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

public class ImageLinkAccessibilityPropertiesValidatorBundle extends ModelValidatorBundle<ImageComponent> {

  public ImageLinkAccessibilityPropertiesValidatorBundle() {
    this.registerValidators();
  }

  @Override
  public void registerValidators() {
    addValidator(getAriaLabelConfiguredValidator());
    addValidator(getLinkTitleConfiguredValidator());
  }

  @Override
  public boolean isAllMustBeTrue() {
    return true;
  }

  @Override
  public String getMessage() {
    return "Image link accessibility properties are configured.";
  }


  ModelValidator getAriaLabelConfiguredValidator() {
    return new ModelValidator<ImageComponent>() {
      @Override
      public Boolean isValidCheck(ImageComponent model) {
        return StringUtils.isNotEmpty(model.getAriaLabel());
      }

      @Override
      public String getMessage() {
        return "Link Aria label is configured.";
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

  ModelValidator getLinkTitleConfiguredValidator() {
    return new ModelValidator<ImageComponent>() {
      @Override
      public Boolean isValidCheck(ImageComponent model) {
        return StringUtils.isNotEmpty(model.getAnchorTitle());
      }

      @Override
      public String getMessage() {
        return "Link title is configured.";
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
