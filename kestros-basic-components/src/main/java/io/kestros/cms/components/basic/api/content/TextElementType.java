package io.kestros.cms.components.basic.api.content;

import javax.annotation.Nonnull;
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

  @Nonnull
  public String getDisplayText() {
    return displayText;
  }

  @Nonnull
  public String getValue() {
    return value;
  }


}
