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

package io.kestros.cms.components.basic.content.card;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Card component.
 */
@KestrosModel(contextModel = CardComponentContext.class)
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/card")
public class CardComponent extends BaseComponent {



  /**
   * Where the button will link off to.
   *
   * @return Where the button will link off to.
   */
  @KestrosProperty(description = "Endpoint the card call to action button links to.")
  public String getLink() {
    return getProperty("link", StringUtils.EMPTY);
  }

  /**
   * Card image.
   *
   * @return Card image.
   */
  @KestrosProperty(description = "Card image.")
  public String getImage() {
    try {
      return SlingModelUtils.getChildAsBaseResource("image", this).getPath();
    } catch (ChildResourceNotFoundException e) {
      return StringUtils.EMPTY;
    }
  }

  /**
   * Card image alt text.
   *
   * @return Card image alt text.
   */
  public String getImageAltText() {
    return getProperty("imageAltText", StringUtils.EMPTY);
  }

  /**
   * Card call to action text.
   *
   * @return Card call to action text.
   */
  public String getCallToActionText() {
    return getProperty("callToActionText", StringUtils.EMPTY);
  }

}
