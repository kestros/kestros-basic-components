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

package io.kestros.cms.components.basic.content.buttongroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kestros.cms.components.basic.content.button.ButtonModel;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Button group component.
 */
@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/button-group")
public class ButtonGroup extends BaseComponent {

  public List<ButtonModel> getButtons() {
    String buttonsJson = getProperty("buttons", "[]");
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(buttonsJson, new TypeReference<List<ButtonGroupButton>>() {
      });
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

}
