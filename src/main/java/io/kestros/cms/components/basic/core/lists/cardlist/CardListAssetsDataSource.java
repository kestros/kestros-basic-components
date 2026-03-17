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
package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.models.AssetCollection;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListAssetsDataSource extends BaseContainerSlingModelDataSource implements
                                                                                KestrosCardList {
  @OSGiService
  private AssetRetrievalService assetRetrievalService;
  private AssetCollection collection;

  String getHeadingLevel() {
    return getResource().getValueMap().get("headingType", "h2");
  }

  AssetCollection getCollection() {
    if (collection == null) {
      String collectionPath = getResource().getValueMap().get("collectionPath", String.class);
      try {
        collection = assetRetrievalService.getCollection(collectionPath, getResourceResolver());
      } catch (AssetCollectionRetrievalException e) {
        return null;
      }
    }
    return collection;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    List<Asset> assets = new ArrayList<>(getCollection().getChildAssets());

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", false);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      assets.sort(Comparator.comparing(a -> {
        switch (sortBy) {
          case "name":
            return a.getName() != null ? a.getName() : "";
          default:
            return a.getTitle() != null ? a.getTitle() : a.getName();
        }
      }));
    }

    if (reverse) {
      Collections.reverse(assets);
    }
    if (limit > 0 && assets.size() > limit) {
      assets = assets.subList(0, limit);
    }

    List<KestrosCard> cards = new ArrayList<>();
    String parentPath = getPath();

    for (Asset asset : assets) {
      String imagePath = asset.getPath();
      String altText = null;
      String caption = null;
      String imageTitle = null;
      String href = null;
      String ariaLabel = null;
      String anchorTitle = null;
      AnchorTarget target = null;

      List<ComponentVariation> titleVariations = getElementVariations("titleVariations",
          KestrosImage.RESOURCE_TYPE);
      String titleLayout = getLayout("title");
      KestrosHeading titleElement = null;
      try {
        titleElement = new KestrosHeadingImpl(asset.getTitle(), "h2",
            this,"title", "titleElement");
      } catch (ComponentConfigurationException e) {
        // do nothing.
      }

      List<ComponentVariation> imageVariations = getElementVariations("imageVariations",
          KestrosImage.RESOURCE_TYPE);
      String imageLayout = getLayout("image");
      String imageId = null;
      KestrosImage image = null;
      try {
        image = new KestrosImageImpl(imagePath, altText, caption, imageTitle,
            href, ariaLabel, anchorTitle, target,
            this, "image", "imageElement",assetRetrievalService);
      } catch (ComponentConfigurationException e) {
        return null;
      }
      try {
        cards.add(
            new KestrosCardImpl(asset.getDescription(), titleElement, image,
                null,
                this,
                "card", null));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ArrayList<>(cards);
  }

}
