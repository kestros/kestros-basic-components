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

package io.kestros.cms.components.basic.content.heading;

import io.kestros.cms.components.basic.content.text.TextComponent;
import io.kestros.cms.components.basic.content.text.TextComponentValidationService;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Basic Heading component.
 */
@KestrosModel(validationService = TextComponentValidationService.class)
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/heading")
public class Heading extends TextComponent {

  /**
   * Heading level.
   *
   * @return Heading level.
   */
  @KestrosProperty(description = "Heading level",
                   defaultValue = "h1",
                   configurable = true,
                   sampleValue = "h1",
                   jcrPropertyName = "level")
  public String getLevel() {
    return getProperty("level", "h1");
  }

}
