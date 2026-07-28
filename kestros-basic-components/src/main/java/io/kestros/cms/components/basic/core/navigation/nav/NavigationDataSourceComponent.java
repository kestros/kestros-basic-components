package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosNavigation} handed to it by an upstream datasource, delegating every
 * value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class NavigationDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosNavigation> implements KestrosNavigation {


  @Nonnull
  @Override
  public List<KestrosNavigationItem> getNavigationLinkElements() {
    return getComponentData().getNavigationLinkElements();
  }
}