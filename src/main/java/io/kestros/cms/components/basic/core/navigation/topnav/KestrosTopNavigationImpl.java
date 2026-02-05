package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.navigation.nav.KestrosNavigationImpl;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosTopNavigationImpl extends KestrosNavigationImpl
    implements KestrosTopNavigation {

  private KestrosImage logo;

  public KestrosTopNavigationImpl(
      @Nullable KestrosImage logo,
      @Nonnull List<KestrosLink> navigationLinks,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(navigationLinks, dataSource, resourcePrefix, forcedResourceName);
    this.logo = logo;
  }

  @Nullable
  @Override
  public KestrosImage getLogo() {
    return logo;
  }
}
