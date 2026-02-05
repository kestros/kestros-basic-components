package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class NavigationDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosNavigation> implements KestrosNavigation {


  @Nonnull
  @Override
  public List<KestrosLink> getNavigationLinks() {
    return getComponentData().getNavigationLinks();
  }
}