package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosBreadCrumbs} handed to it by an upstream datasource, delegating every
 * value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class BreadCrumbsDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosBreadCrumbs> implements KestrosBreadCrumbs {

  @Nonnull
  @Override
  public List<KestrosBreadCrumb> getLinkElements() {
    return getComponentData().getLinkElements();
  }
}