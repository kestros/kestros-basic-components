package io.kestros.cms.components.basic.core;

import javax.annotation.Nullable;

/**
 * Converts multi-byte Unicode characters to numeric HTML entities to work around
 * a Sling HTL engine bug where Content-Length is calculated using character count
 * instead of UTF-8 byte count, causing page output truncation.
 */
public final class TextSanitizer {

  private TextSanitizer() {
  }

  @Nullable
  public static String escapeMultiByteToEntities(@Nullable String text) {
    if (text == null) {
      return null;
    }
    final int length = text.length();
    boolean hasMultiByte = false;
    for (int i = 0; i < length; i++) {
      if (text.charAt(i) > 127) {
        hasMultiByte = true;
        break;
      }
    }
    if (!hasMultiByte) {
      return text;
    }
    final StringBuilder sb = new StringBuilder(length + 32);
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if (c > 127) {
        sb.append("&#").append((int) c).append(';');
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
}
