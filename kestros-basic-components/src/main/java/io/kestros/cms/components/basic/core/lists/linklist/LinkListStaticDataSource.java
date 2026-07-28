package io.kestros.cms.components.basic.core.lists.linklist;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.LinkStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosLinkList} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {
  @Nonnull
  @Override
  public List<KestrosLink> getLinkElements() {
    List<KestrosLink> links = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosLink link = childResource.adaptTo(LinkStaticDataSource.class);
      if (link != null) {
        links.add(link);
      }
    }
    return new ArrayList<>(links);
  }
}
