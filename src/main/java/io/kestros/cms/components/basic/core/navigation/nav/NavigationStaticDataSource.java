package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.LinkStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class NavigationStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosNavigation {
  @Nonnull
  @Override
  public List<KestrosLink> getNavigationLinks() {
    List<KestrosLink> links = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (childResource.getValueMap().get("sling:resourceType", StringUtils.EMPTY).equals(
          KestrosLink.RESOURCE_TYPE)) {
        KestrosLink link = childResource.adaptTo(LinkStaticDataSource.class);
        if (link != null) {
          links.add(link);
        }
      }
    }
    return new ArrayList<>(links);
  }
}
