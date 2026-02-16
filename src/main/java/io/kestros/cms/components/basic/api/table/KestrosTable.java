package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTable extends KestrosBasicComponentElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  List<KestrosTableHeader> getHeaderElements();

  @Nonnull
  default List<Resource> getHeaders() {
    List<Resource> headers = new ArrayList<>();
    for (KestrosTableHeader header : getHeaderElements()) {
      headers.add(header.getResource());
    }
    return headers;
  }

  @Nonnull
  List<KestrosTableRow> getRowElements();

  @Nonnull
  default List<Resource> getRows() {
    List<Resource> rows = new java.util.ArrayList<>();
    for (KestrosTableRow row : getRowElements()) {
      rows.add(row.getResource());
    }
    return rows;
  }
}
