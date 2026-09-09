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
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardAssetDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private static final Logger LOG = LoggerFactory.getLogger(CardAssetDataSource.class);

  private Asset asset;

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public String getDescription() {
    final Asset currentAsset = getAsset();
    return currentAsset != null ? currentAsset.getDescription() : null;
  }

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    final Asset currentAsset = getAsset();
    if (currentAsset == null) {
      return null;
    }
    String headingLevel = getResource().getValueMap().get("headingType", "h1");
    try {
      return new KestrosHeadingImpl(currentAsset.getTitle(), headingLevel,
              this,
              "title",
              "titleElement");
    } catch (ComponentConfigurationException e) {
      LOG.warn("Unable to build the title heading for an asset card; it renders without one.", e);
      return null;
    }
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    final Asset currentAsset = getAsset();
    if (currentAsset == null) {
      return null;
    }
    try {
      return new KestrosImageImpl(currentAsset.getPath(), null, null,
              null, null, null,
              null, AnchorTarget.SAME_WINDOW, this, "image", "imageElement",
              assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      // This method already returns null when there is no asset, so a card whose image cannot be
      // configured renders without one. Rethrowing as RuntimeException failed the whole render.
      LOG.warn("Unable to build the image for an asset card; it renders without one.", e);
      return null;
    }
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
