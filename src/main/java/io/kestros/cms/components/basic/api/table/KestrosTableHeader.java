package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface KestrosTableHeader extends KestrosBasicComponentElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-header";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getText();
}
