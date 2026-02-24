package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;

public interface KestrosTabHeader extends KestrosBasicComponentElement {

  String resourceType = "kestros/components/structure/tabs/tab-header";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return resourceType;
  }

  String getTitle();

  String getTargetContentId();

  Boolean isActive();
}
