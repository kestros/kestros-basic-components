package io.kestros.cms.components.basic.content.image;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.commons.validation.api.ModelValidationMessageType;
import io.kestros.commons.validation.api.models.ModelValidator;
import io.kestros.commons.validation.api.models.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

public class ImagePathValidatorBundle extends ModelValidatorBundle<ImageComponent> {

  public ImagePathValidatorBundle() {
    this.registerValidators();
  }

  @Override
  public void registerValidators() {
    addValidator(getImageIsAssetValidator());
    addValidator(getImageIsExternalValidator());
  }

  @Override
  public boolean isAllMustBeTrue() {
    return false;
  }

  @Override
  public String getMessage() {
    return "Image path is valid.";
  }


  ModelValidator getImageIsAssetValidator() {
    return new ModelValidator<ImageComponent>() {
      @Override
      public Boolean isValidCheck(ImageComponent model) {
        if (StringUtils.isNotEmpty(model.getImagePathPropertyValue()) && model.isAssetResource()) {
          try {
            model.getAsset();
            return true;
          } catch (AssetRetrievalException e) {
            return false;
          }
        }
        return false;
      }

      @Override
      public String getMessage() {
        return "Image is a Kestros asset path.";
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

  ModelValidator getImageIsExternalValidator() {
    return new ModelValidator<ImageComponent>() {
      @Override
      public Boolean isValidCheck(ImageComponent model) {
        return !model.isAssetResource();
      }

      @Override
      public String getMessage() {
        return "Image is an external path.";
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
