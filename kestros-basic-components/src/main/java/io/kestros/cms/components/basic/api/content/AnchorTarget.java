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
  @Nonnull
  public static AnchorTarget lookup(@Nullable String targetValue) {
    if (targetValue != null) {
      // Folded to ASCII rather than compared with equalsIgnoreCase. Every target value is an
      // ASCII token like "_blank", and Unicode case mapping is locale-dependent and can change a
      // string's length, so it is the wrong tool for matching an author's property to a constant.
      final String candidate = toAsciiLowerCase(targetValue);
      for (AnchorTarget anchorTarget : values()) {
        if (anchorTarget.getTargetValue().equals(candidate)) {
          return anchorTarget;
        }
      }
    }
    return SAME_WINDOW;
  }

  /**
   * Lowercases the ASCII letters in a value and leaves every other character alone.
   *
   * @param value Value to fold.
   * @return The value with A-Z folded to a-z.
   */
  @Nonnull
  private static String toAsciiLowerCase(@Nonnull final String value) {
    final char[] characters = value.toCharArray();
    for (int index = 0; index < characters.length; index++) {
      if (characters[index] >= 'A' && characters[index] <= 'Z') {
        characters[index] += 'a' - 'A';
      }
    }
    return new String(characters);
  }

  /**
   * Looks up the AnchorTarget enum based on a boolean indicating if it should open in a new
   * window.
   *
   * @param openInNewWindow Boolean indicating if the link should open in a new window.
   *
   * @return The corresponding AnchorTarget enum.
   */
  @Nonnull
  public static AnchorTarget lookup(@Nullable Boolean openInNewWindow) {
    if (openInNewWindow != null && openInNewWindow) {
      return NEW_WINDOW;
    }
    return SAME_WINDOW;
  }

  /**
   * Looks up the AnchorTarget for a Resource, from its target or openInNewTab property.
   *
   * @param resource Resource to read the target from.
   *
   * @return The corresponding AnchorTarget enum, or SAME_WINDOW if not found.
   */
  @Nonnull
  public static AnchorTarget lookup(@Nullable Resource resource) {
    AnchorTarget target = SAME_WINDOW;
    if (resource != null) {
      String anchorTargetString = resource.getValueMap().get("target", String.class);
      target = AnchorTarget.lookup(anchorTargetString);
      if (target == AnchorTarget.SAME_WINDOW) {
        target = AnchorTarget.lookup(
            resource.getValueMap().get("openInNewTab", Boolean.FALSE));
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
