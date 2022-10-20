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

import io.kestros.cms.modeltypes.annotations.ExternalizedResource;
import io.kestros.cms.modeltypes.html.AnchorModel;
import io.kestros.cms.modeltypes.html.AnchorRelationship;
import io.kestros.cms.modeltypes.html.AnchorTarget;
import io.kestros.cms.modeltypes.html.CrossOrigin;
import io.kestros.cms.modeltypes.html.ImageLoading;
import io.kestros.cms.modeltypes.html.ImageModel;
import io.kestros.cms.modeltypes.html.ReferrerPolicy;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseSite;
import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Image Component.
 */
@KestrosModel()
@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/image")
public class ImageComponent extends BaseComponent implements ImageModel, AnchorModel {

  /**
   * Image path.
   *
   * @return Image path.
   */
  @Deprecated
  @KestrosProperty(description = "Image path.")
  public String getImage() {
    try {
      return getImageResource().getPath();
    } catch (ChildResourceNotFoundException e) {
      return StringUtils.EMPTY;
    } catch (ResourceNotFoundException e) {
      return StringUtils.EMPTY;
    }
  }

  /**
   * Image Resource.
   *
   * @return Image Resource.
   *
   * @throws ChildResourceNotFoundException If image resource is not found.
   */
  @ExternalizedResource(mimeType = "",
      extension = "",
      trimPathToNearest = BaseSite.class)
  public BaseResource getImageResource()
      throws ChildResourceNotFoundException, ResourceNotFoundException {
    String imagePath = getProperty("image", StringUtils.EMPTY);
    if (StringUtils.isEmpty(imagePath)) {
      throw new ChildResourceNotFoundException("Image resource not found.", "");
    }
    BaseResource imageRootAssetResource = SlingModelUtils.getResourceAsBaseResource(imagePath,
        getResourceResolver());
    Resource rendition = imageRootAssetResource.getResource().getChild(
        "jcr:content/renditions/original");
    return SlingModelUtils.adaptToBaseResource(rendition);
  }

  @Override
  @KestrosProperty(description = "Image path.")
  public String getSrc() {
    try {
      return getImageResource().getPath();
    } catch (ChildResourceNotFoundException e) {
      return StringUtils.EMPTY;
    } catch (ResourceNotFoundException e) {
      return StringUtils.EMPTY;
    }
  }

  /**
   * Image alt text.
   *
   * @return Image alt text.
   */
  @Override
  @KestrosProperty(description = "Image alt text.")
  public String getAltText() {
    return getProperty("altText", StringUtils.EMPTY);
  }

  @Override
  public CrossOrigin getCrossOrigin() {
    return null;
  }

  @Override
  public ImageLoading getImageLoading() {
    return null;
  }

  @Override
  public ReferrerPolicy getReferrerPolicy() {
    return null;
  }

  @Override
  public Boolean isDownload() {
    return Boolean.FALSE;
  }

  @Override
  public String getHref() {
    return getProperty("href", StringUtils.EMPTY) + ".html";
  }

  @Override
  public AnchorRelationship getRel() {
    return null;
  }

  @Override
  public AnchorTarget getTarget() {
    return null;
  }
}
