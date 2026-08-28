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
    return getResource().getValueMap().get("headingType", "h2");
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
      KestrosHeading titleElement = null;
      try {
        titleElement = new KestrosHeadingImpl(asset.getTitle(), "h2",
            this, "title", "titleElement");
      } catch (ComponentConfigurationException e) {
        LOG.warn("Unable to build the heading for card {} in {}. The card renders without a "
                + "title. {}", forLog(asset.getPath()), forLog(getResource().getPath()),
            forLog(e.getMessage()), e);
      }

      KestrosImage image = null;
      try {
        image = new KestrosImageImpl(asset.getPath(), null, null, null,
            null, null, null, AnchorTarget.SAME_WINDOW,
            this, "image", "imageElement", assetRetrievalService);
      } catch (ComponentConfigurationException e) {
        // One asset whose image cannot be configured must cost one image, not the whole list.
        // Returning null here blanked every card on the page, and getCardElements is @Nonnull.
        LOG.warn("Unable to build the image for card {} in {}. The card renders without an "
                + "image. {}", forLog(asset.getPath()), forLog(getResource().getPath()),
            forLog(e.getMessage()), e);
      }
      try {
        cards.add(
            new KestrosCardImpl(asset.getDescription(), titleElement, image,
                null,
                this,
                "card", null));
      } catch (ComponentConfigurationException e) {
        // Same reasoning: drop the one card that cannot be configured, keep the rest.
        LOG.warn("Unable to build a card for asset {} in {}. It is left out of the list. {}",
            forLog(asset.getPath()), forLog(getResource().getPath()), forLog(e.getMessage()), e);
      }
    }
    return cards;
  }

  /**
   * Strips CR and LF from a value before it is logged, so an author-controlled path or an exception
   * message cannot forge a log line.
   *
   * @param value Value about to be logged.
   * @return The value with CR and LF removed, or null unchanged.
   */
  @Nullable
  private static String forLog(@Nullable final String value) {
    return value == null ? null : value.replaceAll("[\r\n]", "");
  }

}
