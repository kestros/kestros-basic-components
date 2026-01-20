package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public interface KestrosLinkList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/link-list";

  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  List<KestrosLink> getLinks();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getLinks());
  }
}
