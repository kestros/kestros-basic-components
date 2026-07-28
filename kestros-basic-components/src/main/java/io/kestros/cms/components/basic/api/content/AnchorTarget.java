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
      for (AnchorTarget anchorTarget : values()) {
        if (anchorTarget.getTargetValue().regionMatches(true, 0, targetValue, 0,
            anchorTarget.getTargetValue().length())
            && anchorTarget.getTargetValue().length() == targetValue.length()) {
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
      if (target.equals(AnchorTarget.SAME_WINDOW)) {
        target = AnchorTarget.lookup(resource.getValueMap().get("openInNewTab", Boolean.FALSE));
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
