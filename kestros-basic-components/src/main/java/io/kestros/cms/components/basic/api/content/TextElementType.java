package io.kestros.cms.components.basic.api.content;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.annotation.Nonnull;

@SuppressFBWarnings(value = "METHOD_NULLABILITY",
    justification = "javac 15+ generates a synthetic $values() method on every enum, which source "
        + "cannot annotate, and the Kestros nullability detector exempts values/valueOf but not "
        + "$values. Remove once #1359 fixes the detector. Ruled by Danny, 2026-09-29.")
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
