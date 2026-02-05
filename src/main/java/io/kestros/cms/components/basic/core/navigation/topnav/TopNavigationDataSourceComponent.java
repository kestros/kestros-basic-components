package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
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


  @Nonnull
  @Override
  public List<KestrosLink> getNavigationLinks() {
    return getComponentData().getNavigationLinks();
  }

  @Nullable
  @Override
  public KestrosImage getLogo() {
    return getComponentData().getLogo();
  }
}