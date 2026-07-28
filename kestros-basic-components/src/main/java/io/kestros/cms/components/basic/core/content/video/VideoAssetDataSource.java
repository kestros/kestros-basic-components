package io.kestros.cms.components.basic.core.content.video;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosVideo;
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

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
/**
 * Datasource that resolves video source from a referenced asset. The asset path is used directly
 * as the video source, with fallback text for browsers that do not support the video element.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoAssetDataSource extends BaseSlingModelDataSource implements KestrosVideo {

  private static final Logger LOG = LoggerFactory.getLogger(VideoAssetDataSource.class);

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  private Asset asset;

  @Nullable
  @Override
  public String getVideoSource() {
    Asset videoAsset = getAsset();
    if (videoAsset != null) {
      return videoAsset.getPath();
    }
    return null;
  }

  @Nonnull
  @Override
  public String getFallbackText() {
    return getResource().getValueMap().get("fallbackText", StringUtils.EMPTY);
  }

  @Nullable
  Asset getAsset() {
    if (asset != null) {
      return asset;
    }
    if (assetRetrievalService == null) {
      return null;
    }
    String videoPath = getResource().getValueMap().get("videoPath", String.class);
    if (videoPath != null) {
      try {
        asset = assetRetrievalService.getAsset(videoPath, null, getResourceResolver());
        return asset;
      } catch (AssetRetrievalException e) {
        LOG.warn("Failed to retrieve asset for video: {}",
            videoPath.replaceAll("[\r\n]", ""));
      }
    }
    return null;
  }
}
