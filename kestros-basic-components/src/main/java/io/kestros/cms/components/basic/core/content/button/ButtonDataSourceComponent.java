package io.kestros.cms.components.basic.core.content.button;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import javax.annotation.Nonnull;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ButtonDataSourceComponent extends BaseDataSourceComponent<KestrosButton> implements
        KestrosButton {

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

  @Nullable
  @Override
  public String getTitle() {
    return getComponentData().getTitle();
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return getComponentData().getTarget();
  }

  @Nullable
  @Override
  public String getRel() {
    return getComponentData().getRel();
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getComponentData().getAriaLabel();
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

  @Override
  public boolean isDisabled() {
    return getComponentData().isDisabled();
  }

}
