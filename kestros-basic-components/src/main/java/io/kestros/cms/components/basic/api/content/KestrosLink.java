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
 * API for a basic link component.
 */
public interface KestrosLink extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/link";

  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Visible link text. This should normally be present and meaningful.
   *
   * @return Visible link text.
   */
  @Nullable
  String getText();

  /**
   * Destination URL. Should be null if the link is not navigable.
   *
   * @return Destination URL, or null if the link is not navigable.
   */
  @Nullable
  String getHref();

  /**
   * Target attribute (e.g. "_self", "_blank").
   *
   * @return Target attribute.
   */
  @Nonnull
  AnchorTarget getTarget();

  /**
   * Get the target as a string.
   *
   * @return Target attribute value.
   */
  @Nonnull
  default String getTargetAsString() {
    if (getTarget() != null) {
      return getTarget().getTargetValue();
    }
    return AnchorTarget.SAME_WINDOW.getTargetValue();
  }

  /**
   * ARIA label. Used when visible text is missing or insufficient.
   *
   * @return ARIA label.
   */
  @Nullable
  String getAriaLabel();

  /**
   * Optional title attribute. Use sparingly – should add meaning, not duplicate text.
   *
   * @return Title attribute.
   */
  @Nullable
  String getTitle();

  /**
   * Relationship attribute (e.g. "noopener", "noreferrer", "nofollow").
   *
   * @return Relationship attribute.
   */
  @Nullable
  String getRel();

  /**
   * ARIA described-by ID reference.
   *
   * @return ARIA described-by ID reference.
   */
  @Nullable
  String getAriaDescribedBy();

  /**
   * Language of the link text, if different from the page language. Example: "fr", "de", "en-GB"
   *
   * @return Language of the link text.
   */
  @Nullable
  String getLang();

}
