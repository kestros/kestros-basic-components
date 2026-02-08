package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TopNavigationDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosTopNavigation> implements KestrosTopNavigation {

  private List<KestrosTopNavigationItem> navigationLinks;
  private KestrosImage logo;

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationLinks() {
    if (navigationLinks == null) {
      navigationLinks = getComponentData().getNavigationLinks();
    }
    return navigationLinks;
  }

  @Nullable
  @Override
  public KestrosImage getLogo() {
    if (logo == null) {
      logo = getComponentData().getLogo();
    }
    return logo;
  }
}