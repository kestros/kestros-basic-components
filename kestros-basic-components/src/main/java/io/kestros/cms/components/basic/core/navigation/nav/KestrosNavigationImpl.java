package io.kestros.cms.components.basic.core.navigation.nav;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosNavigationImpl extends BaseContainerSyntheticResource
    implements KestrosNavigation {

  private List<KestrosNavigationItem> navigationLinks;

  public KestrosNavigationImpl(
      @Nonnull List<KestrosNavigationItem> navigationLinks,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.navigationLinks = new ArrayList<>(navigationLinks);
  }

  @Nonnull
  @Override
  public List<KestrosNavigationItem> getNavigationLinkElements() {
    return new ArrayList<>(navigationLinks);
  }
}
