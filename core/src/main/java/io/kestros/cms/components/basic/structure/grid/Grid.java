package io.kestros.cms.components.basic.structure.grid;

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getChildAsType;

import io.kestros.cms.foundation.content.BaseComponent;
import io.kestros.cms.foundation.content.components.contentarea.ContentArea;
import io.kestros.commons.structuredslingmodels.annotation.StructuredModel;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.exceptions.InvalidResourceTypeException;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@StructuredModel(validationService = GridValidationService.class)
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/structure/grid")
public class Grid extends BaseComponent {

  public int getNumberOfColumns() {
    return getProperty("columns", 3);
  }

  public List<ContentArea> getColumns() {
    List<ContentArea> columns = new ArrayList<>();
    for (int i = 1; i < getNumberOfColumns() + 1; i++) {
      try {
        columns.add(getChildAsType("column-" + i, this, ContentArea.class));
      } catch (InvalidResourceTypeException e) {
        // todo log.
      } catch (ChildResourceNotFoundException e) {
        // todo log.
      }
    }
    return columns;
  }
}
