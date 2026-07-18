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

package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Kestros Accordion Panel Element. Individual panel within an accordion structure.
 */
public interface KestrosAccordionPanel extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/structure/accordion-panel";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Gets the title of the accordion panel.
   *
   * @return Title of the accordion panel.
   */
  @Nullable
  String getTitle();

  /**
   * Indicates whether the accordion panel is expanded on load.
   *
   * @return True if the panel is expanded, false otherwise.
   */
  @Nonnull
  Boolean isExpanded();

  /**
   * Indicates whether the accordion panel is disabled.
   *
   * @return True if the panel is disabled, false otherwise.
   */
  @Nonnull
  Boolean isDisabled();

  /**
   * Gets the ARIA label for the accordion panel.
   *
   * @return ARIA label string, or null if not set.
   */
  @Nullable
  String getAriaLabel();
}