package io.kestros.cms.components.basic.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/**
 * Static helpers for working with link hrefs.
 */
public class LinkUtils {
  /**
   * Link external.
   *
   * @param value Value.
   * @return Link external.
   */
  @Nonnull
  public static boolean isLinkExternal(@Nonnull String value) {
    return StringUtils.isNotEmpty(value) && !value.startsWith("/");
  }

  /**
   * Link.
   *
   * @param value Value.
   * @return Link.
   */
  @Nullable
  public static String getLink(@Nullable String value) {
    if (value == null) {
      return null;
    }
    if (isLinkExternal(value)) {
      return value;
    } else {
      if (value.endsWith(".html")) {
        return value;
      } else {
        return value + ".html";
      }
    }
  }

}
