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
