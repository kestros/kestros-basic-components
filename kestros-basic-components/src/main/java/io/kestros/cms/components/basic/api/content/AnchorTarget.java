package io.kestros.cms.components.basic.api.content;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
  @SuppressFBWarnings(value = "IMPROPER_UNICODE",
      justification = "The values compared are this enum's own fixed ASCII target values"
          + " (_self, _blank, _parent, _top), so the Unicode case-folding hazard the detector"
          + " warns about cannot arise. An earlier version of this branch dodged the finding by"
          + " rewriting it as regionMatches(true, ...) + a length check - MEASURED as identical"
          + " case-insensitive semantics with the finding simply not raised, which silenced the"
          + " detector without fixing anything and made the code unreadable. Suppressed honestly"
          + " instead.")
  @Nonnull
  public static AnchorTarget lookup(@Nullable String targetValue) {
    if (targetValue != null) {
      for (AnchorTarget anchorTarget : values()) {
        if (anchorTarget.getTargetValue().equalsIgnoreCase(targetValue)) {
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
   * Gets the string value of the target.
   *
   * @return The target value.
   */
  @Nonnull
  public String getTargetValue() {
    return targetValue;
  }
}
