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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents an alert component that may provide an optional heading and text body.
 * <p>
 * Implementations expose the component's heading and text, and expose the Sling resource type used
 * to render the alert. Nullability of return values is indicated by the {@link Nullable} and
 * {@link Nonnull} annotations.
 */
public interface KestrosAlert extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/alert";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Returns the alert's heading text.
   *
   * @return the heading text, or {@code null} if no heading is configured
   */
  @Nullable
  String getHeading();

  /**
   * Returns the alert's main text content.
   *
   * @return the alert text, or {@code null} if no text is configured
   */
  @Nullable
  String getText();
}