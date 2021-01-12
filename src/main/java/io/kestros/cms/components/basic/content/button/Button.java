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

package io.kestros.cms.components.basic.content.button;

import io.kestros.cms.components.basic.content.text.TextComponent;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Basic button component.
 */
@KestrosModel(contextModel = ComponentRequestContext.class)
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/button")
public class Button extends TextComponent {

  /**
   * Where the button will link off to.
   *
   * @return Where the button will link off to.
   */
  @KestrosProperty(description = "URL the button links to.",
                   jcrPropertyName = "link",
                   configurable = true,
                   sampleValue = "https://kestros.io")
  public String getLink() {
    return getProperty("link", StringUtils.EMPTY);
  }

}
