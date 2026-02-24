package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosTabs extends KestrosContainerElement {
  String RESOURCE_TYPE = "/hold/tabs";

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
  default List<Resource> getTabHeaders() {
    List<Resource> headers = new ArrayList<>();
    for (KestrosTab tab : getTabs()) {
      headers.add(tab.getTabHeader());
    }
    return headers;
  }

  @Nonnull
  default List<Resource> getTabContents() {
    List<Resource> contents = new ArrayList<>();
    for (KestrosTab tab : getTabs()) {
      contents.add(tab.getTabContent());
    }
    return contents;
  }

}
