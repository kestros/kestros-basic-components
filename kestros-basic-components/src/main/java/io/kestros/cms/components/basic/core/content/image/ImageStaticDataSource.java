package io.kestros.cms.components.basic.core.content.image;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ImageStaticDataSource extends BaseSlingModelDataSource implements KestrosImage {

  private static final Logger LOG = LoggerFactory.getLogger(ImageStaticDataSource.class);

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  private Asset asset;

  @Nullable
  @Override
  public String getImageTitle() {
    String assetTitle = "";
    final Asset resolvedAsset = getAsset();
    if (resolvedAsset != null) {
      assetTitle = resolvedAsset.getTitle();
    }
    return getResource().getValueMap().get("imageTitle", assetTitle);
  }

  @Nullable
  @Override
  public String getImagePath() {
    return StringUtils.trimToNull(
            getResource().getValueMap().get("imagePath", String.class));
  }

  @Nullable
  @Override
  public String getHref() {
    return LinkUtils.getLink(getResource().getValueMap().get("href", String.class));
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getResource().getValueMap().get("ariaLabel", String.class);
  }

  @Nullable
  @Override
  public String getAnchorTitle() {
    return getResource().getValueMap().get("anchorTitle", String.class);
  }

  @Nullable
  @Override
  public String getRel() {
    return "";
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return "";
  }

  @Nullable
  @Override
  public String getLang() {
    return "";
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
  }

  /**
   * Text read out in place of the image.
   *
   * <p>The asset fallback below reads the field rather than calling getAsset(), so it only
   * applies when some other getter has already resolved the asset. That is a real defect and it
   * is left alone here: this card is SpotBugs to zero, and changing it changes what
   * ImageStaticDataSourceTest.testGetAltText asserts. Filed separately.
   *
   * @return The configured alt text, the asset's description, or an empty string.
   */
  @Nonnull
  @Override
  public String getAltText() {
    String alt = getResource().getValueMap().get("altText", String.class);

    if (StringUtils.isNotBlank(alt)) {
      return alt;
    }

    if (asset != null && StringUtils.isNotBlank(asset.getDescription())) {
      return asset.getDescription();
    }

    return "";
  }

  @Nullable
  @Override
  public String getCaption() {
    return getResource().getValueMap().get("caption", String.class);
  }

  @Nullable
  Asset getAsset() {
    if (asset != null) {
      return asset;
    }
    final String imagePath = getImagePath();
    try {
      if (imagePath != null) {
        this.asset = assetRetrievalService.getAsset(imagePath, null,
                getResource().getResourceResolver());
        return asset;
      }
    } catch (AssetRetrievalException e) {
      LOG.warn("Failed to retrieve the asset behind an image; the image path is in the "
          + "exception below.", e);
    }
    return null;
  }

}
