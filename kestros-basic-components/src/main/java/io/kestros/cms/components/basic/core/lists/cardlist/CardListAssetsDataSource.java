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
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListAssetsDataSource extends BaseContainerSlingModelDataSource implements
                                                                                KestrosCardList {

  private static final Logger LOG = LoggerFactory.getLogger(CardListAssetsDataSource.class);

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
    AssetCollection col = getCollection();
    if (col == null) {
      return new ArrayList<>();
    }
    CardListSupport.requireComponentPrerequisites(this);
    List<Asset> assets = new ArrayList<>(col.getChildAssets());

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", false);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      switch (sortBy) {
        case "createdDate":
          assets.sort(Comparator.comparing(a -> {
            Date date = a.getCreatedDate();
            return date != null ? date.getTime() : 0L;
          }));
          break;
        case "lastModified":
          assets.sort(Comparator.comparing(a -> {
            Date date = a.getModifiedDate();
            return date != null ? date.getTime() : 0L;
          }));
          break;
        default:
          assets.sort(Comparator.comparing(a -> {
            switch (sortBy) {
              case "name":
                return a.getName() != null ? a.getName() : "";
              default:
                return a.getTitle() != null ? a.getTitle() : a.getName();
            }
          }));
          break;
      }
    }

    if (reverse) {
      Collections.reverse(assets);
    }
    if (limit > 0 && assets.size() > limit) {
      assets = assets.subList(0, limit);
    }

    List<KestrosCard> cards = new ArrayList<>();

    for (Asset asset : assets) {
      String imagePath = readPath(asset);
      try {
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
          titleElement = new KestrosHeadingImpl(asset.getTitle(), getHeadingLevel(),
              this,"title", "titleElement");
        } catch (ComponentConfigurationException e) {
          // The card renders without a title, as it always has. Say so rather than swallowing it.
          CardListSupport.logDegradedCard(LOG, imagePath, getResource().getPath(), "title", e);
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
          // One asset's image is not the list's problem: keep the card, and keep the cards already
          // built for the assets before it. Returning null here handed a null list to HTL.
          CardListSupport.logDegradedCard(LOG, imagePath, getResource().getPath(), "image", e);
        }
        cards.add(
            new KestrosCardImpl(asset.getDescription(), titleElement, image,
                null,
                this,
                "card", null));
      } catch (Exception e) {
        // The prerequisites every card shares were checked above, so this failure belongs to this
        // asset. Drop the asset, keep the rest of the list, and say which asset went and why.
        CardListSupport.logSkippedCard(LOG, imagePath, getResource().getPath(), e);
      }
    }
    return new ArrayList<>(cards);
  }

  /**
   * The asset's path, or null if the asset cannot say where it is. Read before the card is built
   * and outside the try, because it is what names the asset in the log when the card fails.
   *
   * @param asset Asset to read the path from.
   * @return The asset's path, or null if the asset cannot say where it is.
   */
  @Nullable
  private static String readPath(@Nonnull final Asset asset) {
    try {
      return asset.getPath();
    } catch (final RuntimeException e) {
      return null;
    }
  }

}
