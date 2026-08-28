package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.core.content.link.LinkStaticDataSource;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class BreadCrumbStaticDataSource extends LinkStaticDataSource
    implements KestrosBreadCrumb {

  @Nonnull
  @Override
  public Boolean isFirstItem() {
    return getResource().getValueMap().get("firstItem", Boolean.FALSE);
  }

  @Nonnull
  @Override
  public Boolean isLastItem() {
    return getResource().getValueMap().get("lastItem", Boolean.FALSE);
  }
}
