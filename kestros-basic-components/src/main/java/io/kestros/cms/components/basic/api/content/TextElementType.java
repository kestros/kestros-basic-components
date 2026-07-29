package io.kestros.cms.components.basic.api.content;

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

  public String getDisplayText() {
    return displayText;
  }

  public String getValue() {
    return value;
  }


}
