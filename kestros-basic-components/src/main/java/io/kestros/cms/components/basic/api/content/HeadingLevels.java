package io.kestros.cms.components.basic.api.content;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings(value = "METHOD_NULLABILITY",
    justification = "javac 15+ generates a synthetic $values() method on every enum, which source "
        + "cannot annotate, and the Kestros nullability detector exempts values/valueOf but not "
        + "$values. Remove once #1359 fixes the detector. Ruled by Danny, 2026-09-29.")
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

  @Nonnull
  public String getDisplayText() {
    return displayText;
  }

  @Nonnull
  public String getValue() {
    return value;
  }

}
