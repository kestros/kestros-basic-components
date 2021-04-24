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
import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Carousel Component.
 */
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/carousel")
public class CarouselComponent extends BaseComponent {

  /**
   * Slides root resource.
   *
   * @return Slides root resource.
   * @throws ChildResourceNotFoundException Resource not found.
   */
  public BaseResource getSlidesRootResource() throws ChildResourceNotFoundException {
    return SlingModelUtils.getChildAsBaseResource("slides", this);
  }

  /**
   * All contained slides.
   *
   * @return All contained slides.
   */
  public List<CarouselSlide> getSlides() {
    try {
      return SlingModelUtils.getChildrenOfType(getSlidesRootResource(), CarouselSlide.class);
    } catch (ChildResourceNotFoundException e) {
      //      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  /**
   * Previous text.
   *
   * @return Previous text.
   */
  public String getPreviousText() {
    return getProperty("previousText", StringUtils.EMPTY);
  }

  /**
   * Next text.
   *
   * @return Next text.
   */
  public String getNextText() {
    return getProperty("nextText", StringUtils.EMPTY);
  }

  /**
   * Whether to include slide indicators.
   *
   * @return Whether to include slide indicators.
   */
  public Boolean isIncludeIndicators() {
    return getProperty("includeIndicators", Boolean.FALSE);
  }

  /**
   * Whether to include controls.
   *
   * @return Whether to include controls.
   */
  public Boolean isIncludeControls() {
    return getProperty("includeControls", Boolean.FALSE);
  }
}
