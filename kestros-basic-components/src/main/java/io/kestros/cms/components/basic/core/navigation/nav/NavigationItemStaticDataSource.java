package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
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
public class NavigationItemStaticDataSource extends BaseContainerSlingModelDataSource
        implements KestrosNavigationItem {


  @Self
  private LinkStaticDataSource link;

  @Override
  @Nonnull
  public Boolean isActive() {
    return null;
  }

  @Nonnull
  @Override
  public List<KestrosNavigationItem> getNavigationItems() {
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
