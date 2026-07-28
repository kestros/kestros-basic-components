package io.kestros.cms.components.basic.core.content.link;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;


/**
 * Renders a {@link KestrosLink} handed to it by an upstream datasource, delegating every value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkDataSourceComponent extends BaseDataSourceComponent<KestrosLink>
    implements KestrosLink {
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
