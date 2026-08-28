/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LOG.warn("Failed to retrieve asset for video: {}", videoPath);
      }
    }
    return null;
  }
}
