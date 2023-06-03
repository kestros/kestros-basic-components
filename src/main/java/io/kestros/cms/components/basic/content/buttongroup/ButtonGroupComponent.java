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

import io.kestros.cms.components.basic.content.button.ButtonComponent;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

/**
 * Button group component.
 */
@Model(adaptables = Resource.class,
        resourceType = "kestros/commons/components/content/button-group",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ButtonGroupComponent extends BaseComponent {

  @ChildResource
  private List<ButtonComponent> buttons;

  /**
   * Buttons contained in the group.
   *
   * @return Buttons contained in the group.
   */
  @KestrosProperty(description = "Buttons contained in the group.",
          configurable = false)
  public List<ButtonComponent> getButtons() {
    return this.buttons;
  }

}
