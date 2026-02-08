package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TopNavigationStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTopNavigation {

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationLinks() {
    return new ArrayList<>(getChildrenAsType(TopNavigationItemStaticDataSource.class));
  }

  @Nullable
  @Override
  public KestrosImage getLogo() {
    Resource imageResource = getResource().getChild("imageElement");
    if (imageResource == null) {
      imageResource = getResource();
    }
    try {
      return new KestrosImageImpl(imageResource, this, "image", "imageElement",
              assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }
}
