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

package io.kestros.cms.components.basic.content.image;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.content.AnchorTargetOptions;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.commonutils.jcr.JcrPropertyUtils;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image Component.
 */
@KestrosModel()
@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/image")
public class ImageComponent extends BaseComponent {
  private static final Logger LOG = LoggerFactory.getLogger(ImageComponent.class);

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @KestrosProperty(description = "Image path.")
  public String getSrc() {
    if (isAssetResource()) {
      try {
        return getAsset().getPath();
      } catch (AssetRetrievalException e) {
        LOG.error(e.getMessage(), e);
        return null;
      }
    }
    return getImagePathPropertyValue();
  }

  /**
   * Image alt text.
   *
   * @return Image alt text.
   */
  @Nonnull
  @KestrosProperty(description = "Image alt text.")
  public String getAltText() {
    return getProperty("altText", StringUtils.EMPTY);
  }

  @Nonnull
  public String getCaption() {
    return JcrPropertyUtils.getStringOrDefaultValue(getResource(),
        "caption", StringUtils.EMPTY);
  }


  @Nullable
  public String getHref() {
    if (isLink()) {
      if (isLinkExternal()) {
        return getHrefPropertyValue();
      } else {
        return getLinkResource().getPath() + ".html";
      }
    }
    return null;
  }

  @Nonnull
  String getAriaLabel() {
    return JcrPropertyUtils.getStringOrDefaultValue(getResource(),
        "ariaLabel", StringUtils.EMPTY);
  }

  @Nonnull
  public String getAnchorTitle() {
    return JcrPropertyUtils.getStringOrDefaultValue(getResource(),
        "anchorTitle", StringUtils.EMPTY);
  }

  @Nonnull
  public String getTarget() {
    return AnchorTargetOptions.lookup(JcrPropertyUtils.getStringOrDefaultValue(getResource(),
        "target", AnchorTargetOptions.SELF.getValue())).getValue();
  }

  @Nullable
  public String getRole() {
    try {
      if (getAsset().getMimeType().equals("image/svg+xml")) {
        return "img";
      }
    } catch (AssetRetrievalException e) {
      LOG.error(e.getMessage(), e);
    }
    return null;
  }

  boolean isLink() {
    return !StringUtils.isEmpty(getHrefPropertyValue());
  }

  boolean isLinkExternal() {
    return StringUtils.isNotEmpty(getHrefPropertyValue()) && !getHrefPropertyValue().startsWith("/");
  }

  String getHrefPropertyValue() {
    return getProperty("href", StringUtils.EMPTY);
  }

  Resource getLinkResource() {
    if (isLink() && !isLinkExternal()) {
      return getResourceResolver().getResource(getHrefPropertyValue());
    }
    return null;
  }


  String getImagePathPropertyValue() {
    return getProperty("imagePath", StringUtils.EMPTY);
  }

  Asset getAsset() throws AssetRetrievalException {
    if (isAssetResource()) {
      return assetRetrievalService.getAsset(getImagePathPropertyValue(), null,
          getResourceResolver());
    }
    throw new AssetRetrievalException(
        String.format("%s is not a valid asset path, getAsset should not be called.",
            getImagePathPropertyValue()));
  }


  boolean isAssetResource() {
    if (getImagePathPropertyValue().startsWith("/")) {
      return true;
    }
    return false;
  }
}
