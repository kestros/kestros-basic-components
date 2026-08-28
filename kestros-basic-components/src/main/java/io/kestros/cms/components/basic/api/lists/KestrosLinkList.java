package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosLinkList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/link-list";

  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  default List<Resource> getLinks() {
    List<KestrosLink> linkElements = getLinkElements();
    List<Resource> links = new ArrayList<>(linkElements.size());
    for (KestrosLink link : linkElements) {
      links.add(link.getResource());
    }
    return links;
  }

  @Nonnull
  List<KestrosLink> getLinkElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getLinkElements());
  }
}
