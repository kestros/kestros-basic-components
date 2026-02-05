package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public interface KestrosTabs extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/structure/tabs";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  List<KestrosTab> getTabs();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    List<KestrosBasicComponentElement> elements = new ArrayList<>();
    // todo is this right?
    for (KestrosTab tab : getTabs()) {
      elements.add(tab);
    }
    return elements;
  }


  @Nonnull
  default List<KestrosTabHeader> getTabHeaders() {
    List<KestrosTabHeader> headers = new ArrayList<>();
    for (KestrosTab tab : getTabs()) {
      headers.add(tab.getTabHeader());
    }
    return headers;
  }

  @Nonnull
  default List<KestrosTabContent> getTabContents() {
    List<KestrosTabContent> contents = new ArrayList<>();
    for (KestrosTab tab : getTabs()) {
      contents.add(tab.getTabContent());
    }
    return contents;
  }

}
