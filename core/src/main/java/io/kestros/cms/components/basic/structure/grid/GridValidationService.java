package io.kestros.cms.components.basic.structure.grid;

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;

public class GridValidationService extends ModelValidationService {

  @Override
  public Grid getModel() {
    return (Grid) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(columnsAreContentAreas());
  }

  @Override
  public void registerDetailedValidators() {
  }

  ModelValidator columnsAreContentAreas() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return getModel().getColumns().size() == getModel().getNumberOfColumns();
      }

      @Override
      public String getMessage() {
        return "All Columns are instances of ContentArea";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }
}
