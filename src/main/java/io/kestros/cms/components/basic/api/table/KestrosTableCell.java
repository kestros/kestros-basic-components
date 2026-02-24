package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTableCell extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-cell";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  default List<Resource> getCellContent() {
    List<Resource> cellContent = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getCellContentElements()) {
      cellContent.add(element.getResource());
    }
    return cellContent;
  }

  @Nonnull
  List<KestrosBasicComponentElement> getCellContentElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return getCellContentElements();
  }
}
