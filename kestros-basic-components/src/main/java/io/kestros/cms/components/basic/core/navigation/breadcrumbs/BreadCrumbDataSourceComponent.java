package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class BreadCrumbDataSourceComponent
    extends BaseDataSourceComponent<KestrosBreadCrumb> implements KestrosBreadCrumb {

  @Nonnull
  @Override
  public Boolean isFirstItem() {
    return getComponentData().isFirstItem();
  }

  @Nonnull
  @Override
  public Boolean isLastItem() {
    return getComponentData().isLastItem();
  }

  @Nullable
  @Override
  public String getText() {
    return getComponentData().getText();
  }

  @Nullable
  @Override
  public String getHref() {
    return getComponentData().getHref();
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return getComponentData().getTarget();
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getComponentData().getAriaLabel();
  }

  @Nullable
  @Override
  public String getTitle() {
    return getComponentData().getTitle();
  }

  @Nullable
  @Override
  public String getRel() {
    return getComponentData().getRel();
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return getComponentData().getAriaDescribedBy();
  }

  @Nullable
  @Override
  public String getLang() {
    return getComponentData().getLang();
  }


}