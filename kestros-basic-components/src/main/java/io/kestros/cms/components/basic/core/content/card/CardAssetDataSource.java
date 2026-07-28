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
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
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
    final Asset cardAsset = getAsset();
    if (cardAsset == null || cardAsset.getPath() == null) {
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
