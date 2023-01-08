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

package io.kestros.cms.components.basic.content.carousel;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Carousel Component.
 */
@KestrosModel
@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/carousel")
public class CarouselComponent extends BaseComponent {

  /**
   * All contained slides.
   *
   * @return All contained slides.
   */
  @KestrosProperty(description = "slides")
  public List<CarouselSlide> getSlides() {
    return SlingModelUtils.getChildrenOfType(this, CarouselSlide.class);
  }

  /**
   * Previous text.
   *
   * @return Previous text.
   */
  @KestrosProperty(description = "previous text")
  public String getPreviousText() {
    return getProperty("previousText", StringUtils.EMPTY);
  }

  /**
   * Next text.
   *
   * @return Next text.
   */
  @KestrosProperty(description = "next text")
  public String getNextText() {
    return getProperty("nextText", StringUtils.EMPTY);
  }

  /**
   * Whether to include slide indicators.
   *
   * @return Whether to include slide indicators.
   */
  @KestrosProperty(description = "include indicators")
  public Boolean isIncludeIndicators() {
    return getProperty("includeIndicators", Boolean.FALSE);
  }

  /**
   * Whether to include controls.
   *
   * @return Whether to include controls.
   */
  @KestrosProperty(description = "include controls")
  public Boolean isIncludeControls() {
    return getProperty("includeControls", Boolean.FALSE);
  }
}
