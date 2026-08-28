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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Kestros Card component API.
 */
public interface KestrosCard extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/card";

  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resource backing the card's title element, if one was configured.
   *
   * @return Resource backing the card's title element, if one was configured.
   */
  @JsonIgnore
  @Nullable
  default Resource getTitle() {
    if (getResource().getChild("titleElement") != null) {
      return getResource().getChild("titleElement");
    }
    KestrosHeading titleElement = getTitleElement();
    if (titleElement == null) {
      return null;
    }
    return titleElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  /**
   * Card title.
   *
   * @return Title or null.
   */
  @Nullable
  KestrosHeading getTitleElement();

  /**
   * Card description.
   *
   * @return Description or null.
   */
  @Nullable
  String getDescription();

  /**
   * Provides a synthetic resource for the card image.
   *
   * @return Image resource or null.
   */
  @JsonIgnore
  @Nullable
  default Resource getImage() {
    if (getResource().getChild("imageElement") != null) {
      return getResource().getChild("imageElement");
    }
    KestrosImage imageElement = getImageElement();
    if (imageElement == null) {
      return null;
    }
    return imageElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  /**
   * Image element shown on the card, if one was configured.
   *
   * @return Image element shown on the card, if one was configured.
   */
  @Nullable
  KestrosImage getImageElement();

  /**
   * Provides a synthetic resource for the button card group.
   *
   * @return Button group resource or null.
   */
  @JsonIgnore
  @Nullable
  default Resource getButtonGroup() {
    if (getResource().getChild("buttonGroupElement") != null) {
      return getResource().getChild("buttonGroupElement");
    }
    KestrosButtonGroup buttonGroupElement = getButtonGroupElement();
    if (buttonGroupElement == null) {
      return null;
    }
    return buttonGroupElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  /**
   * Button group shown on the card, if one was configured.
   *
   * @return Button group shown on the card, if one was configured.
   */
  @Nullable
  KestrosButtonGroup getButtonGroupElement();

  /**
   * Returns the first button in the button group, used for clickable card layouts.
   *
   * @return Primary button or null.
   */
  @Nullable
  default KestrosButton getPrimaryButton() {
    KestrosButtonGroup group = getButtonGroupElement();
    if (group != null && !group.getButtonsElements().isEmpty()) {
      return group.getButtonsElements().get(0);
    }
    return null;
  }

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    List<KestrosBasicComponentElement> childElements = new ArrayList<>();
    KestrosHeading headingElement = getTitleElement();
    if (headingElement != null) {
      childElements.add(headingElement);
    }
    KestrosImage imageElement = getImageElement();
    if (imageElement != null) {
      childElements.add(imageElement);
    }
    KestrosButtonGroup buttonGroupElement = getButtonGroupElement();
    if (buttonGroupElement != null) {
      childElements.add(buttonGroupElement);
    }
    return childElements;
  }
}
