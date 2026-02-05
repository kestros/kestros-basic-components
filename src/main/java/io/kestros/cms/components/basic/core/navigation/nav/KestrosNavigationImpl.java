package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosNavigationImpl extends BaseContainerSyntheticResource
    implements KestrosNavigation {

  private List<KestrosLink> navigationLinks;

  public KestrosNavigationImpl(
      @Nonnull List<KestrosLink> navigationLinks,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.navigationLinks = navigationLinks;
  }

  @Nonnull
  @Override
  public List<KestrosLink> getNavigationLinks() {
    return new ArrayList<>(navigationLinks);
  }
}
