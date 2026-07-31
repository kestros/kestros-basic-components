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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
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
 * Supplies a {@link KestrosImage} from properties authored on the component's own resource.
 */
@SuppressFBWarnings({"IMC_IMMATURE_CLASS_NO_TOSTRING", "FCBL_FIELD_COULD_BE_LOCAL"})
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ImageStaticDataSource extends BaseSlingModelDataSource implements KestrosImage {

  private static final Logger LOG = LoggerFactory.getLogger(ImageStaticDataSource.class);

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;

  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  private Asset asset;

  @Override
  @Nonnull
  public String getImageTitle() {
    String assetTitle = "";
    final Asset imageAsset = getAsset();
    if (imageAsset != null) {
      assetTitle = imageAsset.getTitle();
    }
    return getResource().getValueMap().get("imageTitle", assetTitle);
  }

  @Nonnull
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
    try {
      final String path = getImagePath();
      if (path != null) {
        this.asset = assetRetrievalService.getAsset(path, null,
                getResource().getResourceResolver());
        return asset;
      }
    } catch (AssetRetrievalException e) {
      LOG.warn("Failed to retrieve asset for image: {}",
          String.valueOf(getImagePath()).replaceAll("[\r\n]", ""));
    }
    return null;
  }

}
