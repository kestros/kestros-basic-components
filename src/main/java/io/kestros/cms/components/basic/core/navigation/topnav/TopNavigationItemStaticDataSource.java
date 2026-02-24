package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.LinkStaticDataSource;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TopNavigationItemStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTopNavigationItem {

  @Self
  private LinkStaticDataSource link;

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationItems() {
    return List.of();
  }

  @Nullable
  @Override
  public String getText() {
    return link.getText();
  }

  @Nullable
  @Override
  public String getHref() {
    return link.getHref();
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return link.getTarget();
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return link.getAriaLabel();
  }

  @Nullable
  @Override
  public String getTitle() {
    return link.getTitle();
  }

  @Nullable
  @Override
  public String getRel() {
    return link.getRel();
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return link.getAriaDescribedBy();
  }

  @Nullable
  @Override
  public String getLang() {
    return link.getLang();
  }
}
