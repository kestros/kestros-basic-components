package io.kestros.cms.components.basic.core.content.card;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.exceptions.ComponentElementRenderingException;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardAssetDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private static final Logger LOG =
      LoggerFactory.getLogger(CardAssetDataSource.class);

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
    final Asset cardAsset = getAsset();
    if (cardAsset == null) {
      return null;
    }
    try {
      return new KestrosHeadingImpl(cardAsset.getTitle(),
              getResource().getValueMap().get("headingType", "h1"),
              this,
              "title",
              "titleElement");
    } catch (ComponentConfigurationException e) {
      LOG.debug("No title element for the card backed by {}: the heading could not be built. {}",
          String.valueOf(cardAsset.getPath()).replaceAll("[\r\n]", ""),
          String.valueOf(e.getMessage()).replaceAll("[\r\n]", ""));
      return null;
    }
  }

  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed ComponentElementRenderingException so the failure"
          + " stays identifiable, per the ruling on DataSourceComponent.")
  @Nullable
  @Override
  public KestrosImage getImageElement() {
    final Asset cardAsset = getAsset();
    // isEmpty rather than an inline == null: Asset declares getPath() @Nonnull, so SpotBugs reads
    // a null check here as redundant. Same behaviour, and it also rejects an empty path.
    if (cardAsset == null || StringUtils.isEmpty(cardAsset.getPath())) {
      return null;
    }
    try {
      // Arguments after the path are altText, caption, imageTitle, href, ariaLabel and
      // anchorTitle. They were locals initialised to null and passed straight through; the
      // variations and layout locals alongside them were computed and never used at all.
      return new KestrosImageImpl(cardAsset.getPath(), null, null,
              null, null, null,
              null, AnchorTarget.SAME_WINDOW, this, "image", "imageElement",
              assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      throw new ComponentElementRenderingException(
              "Unable to build the image element for a card backed by asset "
              + cardAsset.getPath() + ".", e);
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
