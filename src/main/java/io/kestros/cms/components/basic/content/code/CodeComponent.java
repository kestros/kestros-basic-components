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

package io.kestros.cms.components.basic.content.code;

import io.kestros.cms.components.basic.content.text.TextComponent;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Component for displaying text as code snippets.
 */
@KestrosModel()
@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/code")
public class CodeComponent extends TextComponent {


  /**
   * Language to show the code snippet as.
   *
   * @return Language to show the code snippet as.
   */
  @KestrosProperty(description = "Language to show the code snippet as.",
                   configurable = true,
                   sampleValue = "html",
                   defaultValue = "html",
                   jcrPropertyName = "language")
  public String getLanguage() {
    return getProperty("language", "html");
  }

}
