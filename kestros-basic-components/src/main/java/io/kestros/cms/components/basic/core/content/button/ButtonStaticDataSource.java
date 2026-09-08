package io.kestros.cms.components.basic.core.content.button;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import javax.annotation.Nonnull;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ButtonStaticDataSource extends BaseSlingModelDataSource implements KestrosButton {

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }

  @Nullable
  @Override
  public String getHref() {
    return LinkUtils.getLink(getResource().getValueMap().get("href", String.class));
  }

  @Nullable
  @Override
  public String getTitle() {
    return getResource().getValueMap().get("title", String.class);
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
  }

  @Nullable
  @Override
  public String getRel() {
    return getResource().getValueMap().get("rel", String.class);
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getResource().getValueMap().get("ariaLabel", String.class);
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return getResource().getValueMap().get("ariaDescribedBy", String.class);
  }

  @Nullable
  @Override
  public String getLang() {
    return getResource().getValueMap().get("lang", String.class);
  }

  @Override
  public boolean isDisabled() {
    return getResource().getValueMap().get("disabled", false);
  }
}
