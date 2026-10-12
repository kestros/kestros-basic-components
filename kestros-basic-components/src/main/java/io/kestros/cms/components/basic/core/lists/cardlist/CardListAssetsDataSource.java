package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.models.AssetCollection;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
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

  @Nonnull
  String getHeadingLevel() {
    // An unset Heading Level may be saved as an empty string; it renders at the default.
    return StringUtils.defaultIfBlank(getResource().getValueMap().get("headingType", String.class),
        "h2");
  }

  /**
   * Asset collection the cards are built from.
   *
   * @return The configured collection, or null when no collectionPath is set or it cannot be
   *     retrieved.
   */
  @Nullable
  AssetCollection getCollection() {
    if (collection == null) {
      // Defaulted rather than read as String.class: a missing property gave a null path that was
      // handed straight to the asset service. An empty path fails retrieval the same way, without
      // a null crossing the boundary.
      String collectionPath = getResource().getValueMap().get("collectionPath", "");
      try {
        collection = assetRetrievalService.getCollection(collectionPath, getResourceResolver());
      } catch (AssetCollectionRetrievalException e) {
        return null;
      }
    }
    return collection;
  }

  /**
   * Sort key for an asset's creation date, with assets that have no date sorting first.
   *
   * @param asset Asset to read the date from.
   * @return Epoch milliseconds, or 0 when the asset has no creation date.
   */
  @Nonnull
  private static Long getCreatedTime(@Nonnull final Asset asset) {
    final Date date = asset.getCreatedDate();
    return date != null ? date.getTime() : 0L;
  }

  /**
   * Sort key for an asset's last-modified date, with assets that have no date sorting first.
   *
   * @param asset Asset to read the date from.
   * @return Epoch milliseconds, or 0 when the asset has no modification date.
   */
  @Nonnull
  private static Long getModifiedTime(@Nonnull final Asset asset) {
    final Date date = asset.getModifiedDate();
    return date != null ? date.getTime() : 0L;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    AssetCollection col = getCollection();
    if (col == null) {
      return new ArrayList<>();
    }
    // Every card in this list shares these prerequisites, so a failure among them belongs to the
    // whole component and must surface rather than render an empty list.
    IllegalStateException prerequisiteFailure = CardListSupport.componentPrerequisiteFailure(this);
    if (prerequisiteFailure != null) {
      throw prerequisiteFailure;
    }
    List<Asset> assets = new ArrayList<>(col.getChildAssets());

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", Boolean.FALSE);
    int limit;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      switch (sortBy) {
        case "createdDate":
          assets.sort(Comparator.comparing(CardListAssetsDataSource::getCreatedTime));
          break;
        case "lastModified":
          assets.sort(Comparator.comparing(CardListAssetsDataSource::getModifiedTime));
          break;
        case "name":
          assets.sort(Comparator.comparing(Asset::getName));
          break;
        default:
          assets.sort(Comparator.comparing(Asset::getTitle));
          break;
      }
    }

    if (reverse) {
      Collections.reverse(assets);
    }
    if (limit > 0 && assets.size() > limit) {
      assets = assets.subList(0, limit);
    }

    List<KestrosCard> cards = new ArrayList<>(assets.size());

    for (Asset asset : assets) {
      String imagePath = readPath(asset);
      try {
        KestrosHeading titleElement = null;
        try {
          titleElement = new KestrosHeadingImpl(asset.getTitle(), getHeadingLevel(),
              this, "title", "titleElement");
        } catch (ComponentConfigurationException e) {
          // The card renders without a title, as it always has. Say so rather than swallowing it.
          CardListSupport.logDegradedCard(LOG, imagePath, getResource().getPath(), "title", e);
        }

        KestrosImage image = null;
        try {
          image = new KestrosImageImpl(imagePath, null, null, null,
              null, null, null, AnchorTarget.SAME_WINDOW,
              this, "image", "imageElement", assetRetrievalService);
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
    return cards;
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
