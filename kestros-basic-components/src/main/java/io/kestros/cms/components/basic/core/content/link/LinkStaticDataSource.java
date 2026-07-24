package io.kestros.cms.components.basic.core.content.link;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkStaticDataSource extends BaseSlingModelDataSource implements KestrosLink {
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

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource().getValueMap().get("openInNewTab", false));
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getResource().getValueMap().get("ariaLabel", String.class);
  }

  @Nullable
  @Override
  public String getTitle() {
    return getResource().getValueMap().get("title", String.class);
  }

  @Nullable
  @Override
  public String getRel() {
    return getResource().getValueMap().get("rel", String.class);
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
}
