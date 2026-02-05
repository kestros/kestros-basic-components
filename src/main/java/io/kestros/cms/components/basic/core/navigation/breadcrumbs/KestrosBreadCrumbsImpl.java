package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosBreadCrumbsImpl extends BaseContainerSyntheticResource
    implements KestrosBreadCrumbs {

  private List<KestrosBreadCrumb> breadCrumbs;

  public KestrosBreadCrumbsImpl(
      @Nonnull List<KestrosBreadCrumb> breadCrumbs,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.breadCrumbs = breadCrumbs;
  }

  @Nonnull
  @Override
  public List<KestrosBreadCrumb> getLinks() {
    return new ArrayList<>(breadCrumbs);
  }
}
