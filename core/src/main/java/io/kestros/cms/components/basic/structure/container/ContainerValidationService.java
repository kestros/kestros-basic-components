package io.kestros.cms.components.basic.structure.container;

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;

public class ContainerValidationService extends ModelValidationService {

  @Override
  public Container getModel() {
    return (Container) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(hasChild());
  }

  @Override
  public void registerDetailedValidators() {
    return;
  }

  ModelValidator hasChild() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return !getModel().getChildren().isEmpty();
      }

      @Override
      public String getMessage() {
        return "Has at least one child component.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.WARNING;
      }
    };
  }
}
