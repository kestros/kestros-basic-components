package io.kestros.cms.components.basic.core.lists.cardlist;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import io.kestros.cms.components.basic.api.exceptions.ComponentElementRenderingException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.AssetSorter;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Supplies {@link KestrosCardList} built from assets.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListAssetsDataSource extends BaseContainerSlingModelDataSource implements
                                                                                KestrosCardList {
  private static final Logger LOG =
      LoggerFactory.getLogger(CardListAssetsDataSource.class);

  @OSGiService
  private AssetRetrievalService assetRetrievalService;
  private AssetCollection collection;

  @Nonnull
  String getHeadingLevel() {
    return getResource().getValueMap().get("headingType", "h2");
  }

  @Nullable
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

  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed ComponentElementRenderingException so the failure"
          + " stays identifiable, per the ruling on DataSourceComponent.")
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
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    AssetSorter.sort(assets, sortBy);

    if (reverse) {
      Collections.reverse(assets);
    }
    if (limit > 0 && assets.size() > limit) {
      assets = assets.subList(0, limit);
    }

    final List<KestrosCard> cards = new ArrayList<>(assets.size());

    for (final Asset asset : assets) {
      KestrosHeading titleElement = null;
      try {
        titleElement = new KestrosHeadingImpl(asset.getTitle(), "h2",
            this, "title", "titleElement");
      } catch (ComponentConfigurationException e) {
        // A card without its heading still renders; leave it unset.
      }

      final KestrosImage image;
      try {
        // Arguments after the path are altText, caption, imageTitle, href, ariaLabel, anchorTitle
        // and target. They were locals initialised to null and passed straight through; the
        // variations, layout and id locals alongside them were computed and never used.
        image = new KestrosImageImpl(asset.getPath(), null, null, null,
            null, null, null, null,
            this, "image", "imageElement", assetRetrievalService);
      } catch (ComponentConfigurationException e) {
        // Was `return null` from a @Nonnull getter, so one unbuildable image discarded the whole
        // list and handed HTL a null. Skip the card instead, which is what the sibling card lists
        // already do.
        LOG.warn("Skipping card for asset {}: its image could not be built. {}",
            String.valueOf(asset.getPath()).replaceAll("[\r\n]", ""),
            String.valueOf(e.getMessage()).replaceAll("[\r\n]", ""));
        continue;
      }
      try {
        cards.add(
            new KestrosCardImpl(asset.getDescription(), titleElement, image,
                null,
                this,
                "card", null));
      } catch (ComponentConfigurationException e) {
        throw new ComponentElementRenderingException(
            "Unable to build a card for asset " + asset.getPath() + ".", e);
      }
    }
    return new ArrayList<>(cards);
  }

}
