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

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Component that renders a card from the data its datasource supplies.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardDataSourceComponent extends
        BaseContainerDataSourceComponent<KestrosCard> implements KestrosCard {

  private String description;
  private KestrosHeading title;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;


  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    if (title == null) {
      title = getComponentData().getTitleElement();
    }
    return title;
  }

  @Nullable
  @Override
  public String getDescription() {
    if (description == null) {
      description = getComponentData().getDescription();
    }
    return description;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    if (image == null) {
      image = getComponentData().getImageElement();
    }
    return image;
  }


  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    if (buttonGroup == null) {
      buttonGroup = getComponentData().getButtonGroupElement();
    }
    return buttonGroup;
  }


}
