package io.kestros.cms.components.basic.api.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The heading levels values a component may be configured with.
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

  /**
   * Lookup.
   *
   * @param value Value.
   * @return Lookup.
   */
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
