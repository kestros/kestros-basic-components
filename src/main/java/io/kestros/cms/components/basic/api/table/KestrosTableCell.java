package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;

public interface KestrosTableCell extends KestrosBasicComponentElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-cell";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }
}
