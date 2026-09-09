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

import io.kestros.cms.components.basic.api.KestrosContainerElement;
import javax.annotation.Nonnull;

/**
 * Simple container element. Generally these are not styled, but are used to group elements. While
 * they can be styled, it is recommended to use a {@link KestrosSection} for any styled grouping of
 * elements.
 */
public interface KestrosContainer extends KestrosContainerElement {

  String RESOURCE_TYPE = "kestros/components/basic/structure/container";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }
}
