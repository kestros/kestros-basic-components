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

/**
 * The text element type values a component may be configured with.
 */
public enum TextElementType {
  PARAGRAPH("Paragraph", "paragraph"),
  PREFORMATTED("Preformatted", "preformatted"),
  BLOCKQUOTE("Blockquote", "blockquote");

  private String displayText;
  private String value;

  TextElementType(String displayText, String value) {
    this.displayText = displayText;
    this.value = value;
  }

  /**
   * Display text.
   *
   * @return Display text.
   */
  @Nonnull
  public String getDisplayText() {
    return displayText;
  }

  /**
   * Value.
   *
   * @return Value.
   */
  @Nonnull
  public String getValue() {
    return value;
  }


}
