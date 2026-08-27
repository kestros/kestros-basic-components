/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource;
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
  public List<KestrosTopNavigationItem> getNavigationLinkElements() {
    return new ArrayList<>(getChildrenAsType(KestrosTopNavigationItem.RESOURCE_TYPE,
            TopNavigationItemStaticDataSource.class));
  }

  @Nullable
  @Override
  public String getBrandName() {
    return getResource().getValueMap().get("brandName", String.class);
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    Resource imageResource = getResource().getChild("imageElement");
    if (imageResource != null) {
      try {
        return new KestrosImageImpl(imageResource, this, "image", "imageElement",
                assetRetrievalService);
      } catch (ComponentConfigurationException e) {
        // Ignore.
      }
    }
    imageResource = getResource().getChild("image");
    if (imageResource != null) {
      return imageResource.adaptTo(ImageStaticDataSource.class);
    }
    return null;
  }
}
