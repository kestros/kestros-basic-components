package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTableRow extends KestrosBasicComponentElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-row";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }




  @Nonnull
  List<KestrosTableCell> getCellElements();

  @Nonnull
  default List<Resource> getCells() {
    List<Resource> cells = new ArrayList<>();
    for (KestrosTableCell cell : getCellElements()) {
      cells.add(cell.getResource());
    }
    return cells;
  }
}