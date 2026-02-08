package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class NavigationItemStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosNavigationItem {


  @Nonnull
  @Override
  public List<KestrosNavigationItem> getNavigationItems() {
    return List.of();
  }

  @Nullable
  @Override
  public String getText() {
    return "";
  }

  @Nullable
  @Override
  public String getHref() {
    return "";
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return null;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return "";
  }

  @Nullable
  @Override
  public String getTitle() {
    return "";
  }

  @Nullable
  @Override
  public String getRel() {
    return "";
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return "";
  }

  @Nullable
  @Override
  public String getLang() {
    return "";
  }

}
