package io.kestros.cms.components.basic.core.content.video;

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

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoStaticDataSource extends BaseSlingModelDataSource implements KestrosVideo {
  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public String getVideoSource() {
    try {
      return getVideoAsset().getPath();
    } catch (AssetRetrievalException e) {
      return null;
    }
  }

  @Nonnull
  @Override
  public String getFallbackText() {
    return getResource().getValueMap().get("fallbackText", StringUtils.EMPTY);
  }

  /**
   * The configured video path.
   *
   * <p>Empty rather than null when no video has been chosen, so the @Nonnull this already
   * declared is true. An empty path fails asset retrieval, which getVideoSource already
   * handles, instead of reaching the retrieval service as null.</p>
   *
   * @return The video path, or the empty string when none is set.
   */
  @Nonnull
  String getVideoPath() {
    return getResource().getValueMap().get("videoPath", StringUtils.EMPTY);
  }

  @Nullable
  Asset getVideoAsset() throws AssetRetrievalException {
    if (assetRetrievalService != null) {
      return assetRetrievalService.getAsset(getVideoPath(), null, getResourceResolver());
    }
    return null;
  }
}
