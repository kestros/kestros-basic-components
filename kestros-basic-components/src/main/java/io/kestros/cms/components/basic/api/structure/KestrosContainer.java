package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosContainerElement;
import javax.annotation.Nonnull;

/**
 * Simple container element. Generally these are not styled, but are used to group elements. While
 * they can be styled, it is recommended to use a {@link KestrosSection} for any styled grouping of
 * elements.
 */
public interface KestrosContainer extends KestrosContainerElement {

  String RESOURCE_TYPE = "kestros/components/basic/structure/container";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }
}
