package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosTopNavigationImpl extends BaseContainerSyntheticResource
    implements KestrosTopNavigation {

  private List<KestrosTopNavigationItem> navigationLinks;
  private KestrosImage logo;

  public KestrosTopNavigationImpl(
      @Nullable KestrosImage logo,
      @Nonnull List<KestrosTopNavigationItem> navigationLinks,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.logo = logo;
    this.navigationLinks = navigationLinks;
  }

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationLinks() {
    return new ArrayList<>(navigationLinks);
  }

  @Nullable
  @Override
  public KestrosImage getLogo() {
    return logo;
  }
}
