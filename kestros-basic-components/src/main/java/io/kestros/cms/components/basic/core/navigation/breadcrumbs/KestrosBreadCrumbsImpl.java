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

@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A style rule rather than a defect: the detector fires on every class that"
        + " does not declare toString. The fields here are author content read from the"
        + " repository and are reached through the getters HTL calls, so a default rendering of"
        + " them is not something to add.")
public class KestrosBreadCrumbsImpl extends BaseContainerSyntheticResource
    implements KestrosBreadCrumbs {

  private List<KestrosBreadCrumb> breadCrumbs;

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
