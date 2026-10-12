package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
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
  public List<KestrosNavigationItem> getNavigationLinkElements() {
    List<KestrosNavigationItem> links = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (KestrosNavigationItem.RESOURCE_TYPE.equals(
          childResource.getValueMap().get("sling:resourceType", StringUtils.EMPTY))) {
        KestrosNavigationItem link = childResource.adaptTo(NavigationItemStaticDataSource.class);
        if (link != null) {
          links.add(link);
        }
      }
    }
    return new ArrayList<>(links);
  }
}
