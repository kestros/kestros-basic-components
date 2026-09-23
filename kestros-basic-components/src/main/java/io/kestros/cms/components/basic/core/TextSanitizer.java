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

package io.kestros.cms.components.basic.core;

import javax.annotation.Nullable;

/**
 * Converts multi-byte Unicode characters to numeric HTML entities to work around
 * a Sling HTL engine bug where Content-Length is calculated using character count
 * instead of UTF-8 byte count, causing page output truncation.
 */
public final class TextSanitizer {

  private TextSanitizer() {
  }

  /**
   * Replaces the multi-byte characters in the given text with their HTML entities.
   *
   * @param text Text to escape.
   * @return Escaped text, or null if no text was given.
   */
  @Nullable
  public static String escapeMultiByteToEntities(@Nullable String text) {
    if (text == null) {
      return null;
    }
    boolean hasMultiByte = false;
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) > 127) {
        hasMultiByte = true;
        break;
      }
    }
    if (!hasMultiByte) {
      return text;
    }
    StringBuilder sb = new StringBuilder(text.length() + 32);
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (c > 127) {
        sb.append("&#").append((int) c).append(';');
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
}
