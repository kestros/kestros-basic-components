package io.kestros.cms.components.basic.api.structure;

import javax.annotation.Nonnull;

/**
 * Tab content component for Kestros Tabs.
 */
public interface KestrosTabContent extends KestrosContainer {

  String resourceType = "kestros/components/structure/tabs/tab-content";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return resourceType;
  }
}
