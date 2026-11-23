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

import javax.annotation.Nullable;

/**
 * Section container element. Generally these are styled groupings of elements, often with
 * background images or colors.
 */
public interface KestrosSection extends KestrosContainer {

  /**
   * Background image for the section.
   *
   * @return Background image for the section.
   */
  @Nullable
  String getBackgroundImage();
}
