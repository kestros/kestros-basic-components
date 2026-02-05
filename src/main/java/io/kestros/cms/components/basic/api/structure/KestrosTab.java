package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;

public interface KestrosTab extends KestrosBasicComponentElement {

  String resourceType = "kestros/components/structure/tabs/tab";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return resourceType;
  }

  KestrosTabHeader getTabHeader();

  KestrosTabContent getTabContent();
}
