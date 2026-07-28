package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * API for the link list component element.
 */
public interface KestrosLinkList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/link-list";

  /**
   * Component resource type.
   *
   * @return Component resource type.
   */
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Links.
   *
   * @return Links.
   */
  @Nonnull
  default List<Resource> getLinks() {
    final List<KestrosLink> sourceLinks = getLinkElements();
    final List<Resource> links = new ArrayList<>(sourceLinks.size());
    for (KestrosLink link : sourceLinks) {
      links.add(link.getResource());
    }
    return links;
  }

  /**
   * Link elements.
   *
   * @return Link elements.
   */
  @Nonnull
  List<KestrosLink> getLinkElements();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getLinkElements());
  }
}
