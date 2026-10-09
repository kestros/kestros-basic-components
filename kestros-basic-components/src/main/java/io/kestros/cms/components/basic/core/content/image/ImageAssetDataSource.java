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

package io.kestros.cms.components.basic.core.content.image;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
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
 * Datasource that resolves image properties from a referenced asset path. The asset's title,
 * description, and path are used to populate the image component fields.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ImageAssetDataSource extends BaseSlingModelDataSource implements KestrosImage {

  private static final Logger LOG = LoggerFactory.getLogger(ImageAssetDataSource.class);

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  private Asset asset;

  @Nullable
  @Override
  public String getImagePath() {
    return StringUtils.trimToNull(
        getResource().getValueMap().get("imagePath", String.class));
  }

  @Override
  public String getImageTitle() {
    if (getAsset() != null && StringUtils.isNotBlank(getAsset().getTitle())) {
      return getAsset().getTitle();
    }
    return getResource().getValueMap().get("imageTitle", "");
  }

  @Nonnull
  @Override
  public String getAltText() {
    String alt = getResource().getValueMap().get("altText", String.class);
    if (StringUtils.isNotBlank(alt)) {
      return alt;
    }
    if (getAsset() != null && StringUtils.isNotBlank(getAsset().getDescription())) {
      return getAsset().getDescription();
    }
    return "";
  }

  @Nullable
  @Override
  public String getCaption() {
    return getResource().getValueMap().get("caption", String.class);
  }

  @Nullable
  @Override
  public String getHref() {
    return null;
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return AnchorTarget.SAME_WINDOW;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getResource().getValueMap().get("ariaLabel", String.class);
  }

  @Nullable
  @Override
  public String getAnchorTitle() {
    return null;
  }

  @Nullable
  @Override
  public String getRel() {
    return null;
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return null;
  }

  @Nullable
  @Override
  public String getLang() {
    return null;
  }

  @Nullable
  Asset getAsset() {
    if (asset != null) {
      return asset;
    }
    if (assetRetrievalService == null) {
      return null;
    }
    try {
      if (getImagePath() != null) {
        this.asset = assetRetrievalService.getAsset(getImagePath(), null,
            getResource().getResourceResolver());
        return asset;
      }
    } catch (AssetRetrievalException e) {
      LOG.warn("Failed to retrieve asset for image: {}", getImagePath());
    }
    return null;
  }
}
