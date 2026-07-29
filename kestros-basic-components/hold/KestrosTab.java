package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTab extends KestrosBasicComponentElement {

  String resourceType = "kestros/components/structure/tabs/tab";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return resourceType;
  }

  default Resource getTabHeader() {
    return getTabHeaderElement().toSyntheticResource(getResourceResolver(), getPath());
  }

  KestrosTabHeader getTabHeaderElement();

  KestrosTabContent getTabContentElement();

  default Resource getTabContent() {
    return getTabContentElement().toSyntheticResource(getResourceResolver(), getPath());
  }
}
