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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Enum representing anchor target options for links.
 */
public enum AnchorTarget {

  SAME_WINDOW("_self"),
  NEW_WINDOW("_blank");

  private final String targetValue;

  /**
   * Constructor for AnchorTarget enum.
   *
   * @param targetValue The string value representing the target.
   */
  AnchorTarget(@Nonnull String targetValue) {
    this.targetValue = targetValue;
  }

  /**
   * Looks up the AnchorTarget enum based on the provided target value.
   *
   * @param targetValue The target value to look up.
   *
   * @return The corresponding AnchorTarget enum, or SAME_WINDOW if not found.
   */
  public static AnchorTarget lookup(@Nullable String targetValue) {
    if (targetValue != null) {
      for (AnchorTarget anchorTarget : values()) {
        if (anchorTarget.getTargetValue().equalsIgnoreCase(targetValue)) {
          return anchorTarget;
        }
      }
    }
    return SAME_WINDOW;
  }

  /**
   * Looks up the AnchorTarget enum based on a boolean indicating if it should open in a new
   * window.
   *
   * @param openInNewWindow Boolean indicating if the link should open in a new window.
   *
   * @return The corresponding AnchorTarget enum.
   */
  public static AnchorTarget lookup(@Nullable Boolean openInNewWindow) {
    if (openInNewWindow != null && openInNewWindow) {
      return NEW_WINDOW;
    }
    return SAME_WINDOW;
  }

  public static AnchorTarget lookup(@Nullable Resource resource) {
    AnchorTarget target = SAME_WINDOW;
    if (resource != null) {
      String anchorTargetString = resource.getValueMap().get("target", String.class);
      target = AnchorTarget.lookup(anchorTargetString);
      if (target.equals(AnchorTarget.SAME_WINDOW)) {
        target = AnchorTarget.lookup(resource.getValueMap().get("openInNewTab", false));
      }
    }
    return target;
  }

  /**
   * Gets the string value of the target.
   *
   * @return The target value.
   */
  @Nonnull
  public String getTargetValue() {
    return targetValue;
  }
}
