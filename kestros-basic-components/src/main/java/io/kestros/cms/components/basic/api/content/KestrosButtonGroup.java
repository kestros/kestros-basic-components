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

package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Group of buttons rendered together.
 */
public interface KestrosButtonGroup extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/button-group";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resources backing the buttons in the group.
   *
   * @return Resources backing the buttons in the group.
   */
  @Nonnull
  default List<Resource> getButtons() {
    List<KestrosButton> buttonElements = getButtonsElements();
    List<Resource> buttons = new ArrayList<>(buttonElements.size());
    for (KestrosButton button : buttonElements) {
      buttons.add(button.getResource());
    }
    return buttons;
  }

  /**
   * Buttons the group holds.
   *
   * @return Buttons the group holds.
   */
  @Nonnull
  List<KestrosButton> getButtonsElements();

  /**
   * Variations applied to the buttons in the group.
   *
   * @return Variations applied to the buttons in the group.
   */
  @Nonnull
  List<ComponentVariation> getButtonVariations();

  /**
   * Buttons of the group, as generic child elements.
   *
   * @return Buttons of the group, as generic child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getButtonsElements());
  }
}
