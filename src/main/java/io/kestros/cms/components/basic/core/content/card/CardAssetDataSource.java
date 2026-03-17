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
package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardAssetDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private Asset asset;

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public String getDescription() {
    if (getAsset() != null) {
      return getAsset().getDescription();
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    if (getAsset() != null) {
      String title = getAsset().getTitle();
      String headingLevel = getResource().getValueMap().get("headingType", "h1");
      try {
        return new KestrosHeadingImpl(title, headingLevel,
                this,
                "title",
                "titleElement");
      } catch (ComponentConfigurationException e) {
        // do nothing.
      }
      return null;
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    if (getAsset() != null) {
      if (getAsset().getPath() != null) {
        String imagePath = getAsset().getPath();
        String altText = null;
        String caption = null;
        String imageTitle = null;
        String href = null;
        String ariaLabel = null;
        String anchorTitle = null;
        AnchorTarget target = AnchorTarget.SAME_WINDOW;
        String id = null;
        List<ComponentVariation> componentVariations
                = getElementVariations("imageVariations", KestrosImage.RESOURCE_TYPE);
        String layout = getLayout("image");
        try {
          return new KestrosImageImpl(imagePath, altText, caption,
                  imageTitle, href, ariaLabel,
                  anchorTitle, target, this, "image", "imageElement", assetRetrievalService);
        } catch (ComponentConfigurationException e) {
          throw new RuntimeException(e);
        }
      }
      return null;
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    return null;
  }

  @Nullable
  Asset getAsset() {
    if(assetRetrievalService == null) {
      return null;
    }
    if (asset == null) {
      String assetPath = getResource().getValueMap().get("imagePath", String.class);
      if (assetPath != null) {
        try {
          asset = assetRetrievalService.getAsset(assetPath, null, getResourceResolver());
        } catch (AssetRetrievalException e) {
          return null;
        }
      }
    }
    return asset;
  }
}
