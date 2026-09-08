package io.kestros.cms.components.basic.api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTableRow extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-row";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }


  @Nonnull
  List<KestrosTableCell> getCellElements();

  @JsonIgnore
  @Nonnull
  default List<Resource> getCells() {
    final List<KestrosTableCell> sourceCells = getCellElements();
    final List<Resource> cells = new ArrayList<>(sourceCells.size());
    for (KestrosTableCell cell : sourceCells) {
      if (cell.isSynthetic()) {
        cells.add(cell.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        cells.add(cell.getResource());
      }
    }
    return cells;
  }

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getCellElements());
  }
}