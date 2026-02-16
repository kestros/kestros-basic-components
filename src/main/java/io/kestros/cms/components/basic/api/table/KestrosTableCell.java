package io.kestros.cms.components.basic.api.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface KestrosTableCell extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/table-cell";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getText();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    throw new UnsupportedOperationException(
        "KestrosTableCell does not support getChildElements().");
  }
}
