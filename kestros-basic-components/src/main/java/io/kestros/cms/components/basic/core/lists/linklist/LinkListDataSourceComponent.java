package io.kestros.cms.components.basic.core.lists.linklist;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosLinkList} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListDataSourceComponent extends BaseContainerDataSourceComponent<KestrosLinkList>
    implements KestrosLinkList {

  @Nonnull
  @Override
  public List<KestrosLink> getLinkElements() {
    return getComponentData().getLinkElements();
  }
}