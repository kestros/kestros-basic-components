package io.kestros.cms.components.basic.core.navigation.topnav;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

/**
 * Programmatic {@link KestrosTopNavigation}, built in code by a datasource rather than adapted from
 * an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosTopNavigationImpl extends BaseContainerSyntheticResource
        implements KestrosTopNavigation {

  private List<KestrosTopNavigationItem> navigationLinks;
  private KestrosImage logo;
  private String brandName;

  /**
   * Constructs a top navigation impl.
   *
   * @param brandName Brand name.
   * @param logo Logo.
   * @param navigationLinks Navigation links.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosTopNavigationImpl(
          @Nullable String brandName,
          @Nullable KestrosImage logo,
          @Nonnull List<KestrosTopNavigationItem> navigationLinks,
          @Nonnull BaseSlingModelDataSource dataSource,
          @Nonnull String resourcePrefix, String forcedResourceName)
          throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.logo = logo;
    this.brandName = brandName;
    this.navigationLinks = new ArrayList<>(navigationLinks);
  }

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationLinkElements() {
    return new ArrayList<>(navigationLinks);
  }

  @Nullable
  @Override
  public String getBrandName() {
    return brandName;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    return logo;
  }
}
