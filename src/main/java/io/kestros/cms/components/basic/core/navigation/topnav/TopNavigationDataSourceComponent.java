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
  public List<KestrosTopNavigationItem> getNavigationLinkElements() {
    if (navigationLinks == null) {
      navigationLinks = getComponentData().getNavigationLinkElements();
    }
    return navigationLinks;
  }

  @Nullable
  @Override
  public String getBrandName() {
    return getComponentData().getBrandName();
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
      if (logo == null) {
      logo = getComponentData().getImageElement();
    }
    return logo;
  }
}