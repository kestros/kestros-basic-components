package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTable extends KestrosContainerElement {
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
    final List<KestrosTableHeader> sourceHeaders = getHeaderElements();
    final List<Resource> headers = new ArrayList<>(sourceHeaders.size());
    for (KestrosTableHeader header : sourceHeaders) {
      if (header.isSynthetic()) {
        headers.add(header.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        headers.add(header.getResource());
      }
    }
    return headers;
  }

  @Nonnull
  List<KestrosTableRow> getRowElements();

  @Nonnull
  default List<Resource> getRows() {
    final List<KestrosTableRow> sourceRows = getRowElements();
    final List<Resource> rows = new ArrayList<>(sourceRows.size());
    for (KestrosTableRow row : sourceRows) {
      if (row.isSynthetic()) {
        rows.add(row.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        rows.add(row.getResource());
      }
    }
    return rows;
  }

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getRowElements());
  }
}
