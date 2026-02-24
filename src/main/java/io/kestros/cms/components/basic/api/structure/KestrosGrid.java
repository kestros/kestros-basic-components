package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Simple grid component. Grid configurations are provided via view layouts or variations.
 */
public interface KestrosGrid extends KestrosContainerElement {

  String RESOURCE_TYPE = "kestros/components/basic/structure/grid";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>();
  }
}
