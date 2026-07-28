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

package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

/**
 * Supplies a {@link KestrosCard} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardStaticDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public String getDescription() {
    return getResource().getValueMap().get("description", String.class);
  }

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    Resource titleResource = getResource().getChild("titleElement");
    if (titleResource == null) {
      titleResource = getResource();
    }
    try {
      return new KestrosHeadingImpl(titleResource, this, "title", "titleElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    try {
      Resource imageResource = getResource().getChild("imageElement");
      if (imageResource == null) {
        imageResource = getResource();
      }
      return new KestrosImageImpl(imageResource, this,
              "image",
              "imageElement", assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    Resource buttonGroupResource = getResource().getChild("buttonGroupElement");
    if (buttonGroupResource == null) {
      buttonGroupResource = getResource();
    }
    try {
      return new KestrosButtonGroupImpl(buttonGroupResource,
              this, "buttonGroup", "buttonGroupElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


}