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

/**
 * Heading levels a heading element can render at.
 */
public enum HeadingLevels {
  H1("Heading 1", "h1"),
  H2("Heading 2", "h2"),
  H3("Heading 3", "h3"),
  H4("Heading 4", "h4"),
  H5("Heading 5", "h5"),
  H6("Heading 6", "h6");

  private String displayText;
  private String value;

  HeadingLevels(String displayText, String value) {
    this.displayText = displayText;
    this.value = value;
  }

  @Nullable
  public static HeadingLevels lookup(@Nonnull String value) {
    for (HeadingLevels level : HeadingLevels.values()) {
      if (level.getValue().equals(value)) {
        return level;
      }
    }
    return null;
  }

  /**
   * Text shown for this level in the authoring dialog.
   *
   * @return Text shown for this level in the authoring dialog.
   */
  public String getDisplayText() {
    return displayText;
  }

  /**
   * Value stored for this level.
   *
   * @return Value stored for this level.
   */
  public String getValue() {
    return value;
  }

}
