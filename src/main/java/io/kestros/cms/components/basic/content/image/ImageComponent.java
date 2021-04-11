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

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Image Component.
 */
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/image-component")
public class ImageComponent extends BaseComponent {

  /**
   * Image path.
   *
   * @return Image path.
   */
  @KestrosProperty(description = "Image path.")
  public String getImage() {
    try {
      return SlingModelUtils.getChildAsBaseResource("image", this).getPath();
    } catch (ChildResourceNotFoundException e) {
      return StringUtils.EMPTY;
    }
  }

  /**
   * Image alt text.
   *
   * @return Image alt text.
   */
  public String getAltText() {
    return getProperty("altText", StringUtils.EMPTY);
  }

}
