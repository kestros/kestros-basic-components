package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Programmatic {@link KestrosBreadCrumbs}, built in code by a datasource rather than adapted from
 * an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosBreadCrumbsImpl extends BaseContainerSyntheticResource
    implements KestrosBreadCrumbs {

  private List<KestrosBreadCrumb> breadCrumbs;

  /**
   * Constructs a bread crumbs impl.
   *
   * @param breadCrumbs Bread crumbs.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosBreadCrumbsImpl(
      @Nonnull List<KestrosBreadCrumb> breadCrumbs,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.breadCrumbs = new ArrayList<>(breadCrumbs);
  }

  @Nonnull
  @Override
  public List<KestrosBreadCrumb> getLinkElements() {
    return new ArrayList<>(breadCrumbs);
  }


}
