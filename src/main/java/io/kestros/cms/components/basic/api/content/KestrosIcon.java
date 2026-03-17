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
 * Represents an icon component that supports font icon (CSS class), SVG, and image rendering modes.
 * <p>
 * The rendering mode is determined by the {@link #getIconType()} property. Each mode uses different
 * properties for rendering. Accessibility attributes are handled per-mode.
 */
public interface KestrosIcon extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/icon";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Returns the icon rendering type: {@code font}, {@code svg}, or {@code image}.
   *
   * @return the icon type string, defaulting to {@code "font"} if not configured
   */
  @Nonnull
  String getIconType();

  /**
   * Returns the CSS class string for font icon mode (e.g. {@code "fa fa-star"}).
   * Used when {@link #getIconType()} is {@code "font"}.
   *
   * @return the CSS class string, or empty string if not configured
   */
  @Nonnull
  String getIconClass();

  /**
   * Returns the DAM asset path for SVG or image mode.
   * Used when {@link #getIconType()} is {@code "svg"} or {@code "image"}.
   *
   * @return the asset path, or empty string if not configured
   */
  @Nonnull
  String getIconPath();

  /**
   * Returns the alt text for accessibility in SVG and image modes.
   *
   * @return the alt text, or empty string if not configured
   */
  @Nonnull
  String getAltText();

  /**
   * Returns whether this icon should be hidden from screen readers.
   * When {@code true}, {@code aria-hidden="true"} is applied (decorative icon use).
   *
   * @return {@code true} if the icon should be hidden from screen readers
   */
  boolean getHideFromScreenReader();

  /**
   * Returns an optional link URL to wrap the icon in an anchor element.
   * Applies to image mode.
   *
   * @return the link URL, or empty string if not configured
   */
  @Nonnull
  String getHref();

  /**
   * Returns an optional CSS size modifier class (e.g. {@code "kbc-icon--sm"}).
   *
   * @return the size class, or empty string if not configured
   */
  @Nonnull
  String getSizeClass();
}
