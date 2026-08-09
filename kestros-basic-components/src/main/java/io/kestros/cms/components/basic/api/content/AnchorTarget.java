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
      String foldedTargetValue = toAsciiLowerCase(targetValue);
      for (AnchorTarget anchorTarget : values()) {
        if (anchorTarget.getTargetValue().equals(foldedTargetValue)) {
          return anchorTarget;
        }
      }
    }
    return SAME_WINDOW;
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

  @Nonnull
  public static AnchorTarget lookup(@Nullable Resource resource) {
    AnchorTarget target = SAME_WINDOW;
    if (resource != null) {
      String anchorTargetString = resource.getValueMap().get("target", String.class);
      target = AnchorTarget.lookup(anchorTargetString);
      if (target == AnchorTarget.SAME_WINDOW) {
        target = AnchorTarget.lookup(resource.getValueMap().get("openInNewTab", Boolean.FALSE));
      }
    }
    return target;
  }

  /**
   * Lowercases the ASCII letters A-Z and leaves every other character untouched.
   *
   * <p>This exists instead of {@code equalsIgnoreCase} or {@code toLowerCase(Locale)} because both
   * of those apply full Unicode case folding, under which characters that are not ASCII compare
   * equal to ASCII ones - {@code U+212A KELVIN SIGN} folds to {@code k}, so {@code "_blanK"}
   * would have matched {@code _blank}. Every target value this enum declares is ASCII, so folding
   * beyond ASCII can only ever create a false match. Restricting the fold removes that, which is
   * also what SpotBugs' IMPROPER_UNICODE is pointing at.</p>
   *
   * @param value The value to fold.
   *
   * @return The value with ASCII A-Z lowercased.
   */
  @Nonnull
  private static String toAsciiLowerCase(@Nonnull String value) {
    int length = value.length();
    StringBuilder folded = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char character = value.charAt(i);
      if (character >= 'A' && character <= 'Z') {
        folded.append((char) (character - 'A' + 'a'));
      } else {
        folded.append(character);
      }
    }
    return folded.toString();
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
