/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.content.videoembed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defense-in-depth sanitizer for video embed HTML output. Validates that embed code
 * contains only a safe iframe tag from an allowlisted domain. Strips script tags,
 * event handlers, and dangerous URI schemes.
 *
 * <p>This is a second line of defense — the primary validation happens in individual
 * datasource implementations (e.g., VideoEmbedYouTubeDataSource). This sanitizer
 * guards against future datasource variants that might not validate as rigorously.</p>
 */
@SuppressFBWarnings(value = {"IMPROPER_UNICODE", "DM_CONVERT_CASE"},
    justification = "Attribute names and domains are case-folded with Locale.ROOT"
        + " before being matched against a fixed allow-list. The detector warns that"
        + " case mapping can change a string's length; here the folded value is only"
        + " ever compared to lower-case ASCII constants, so a character that folds"
        + " differently fails the match rather than passing it.")
public final class EmbedSanitizer {

  private EmbedSanitizer() {
    // static utility class
  }

  private static final Set<String> ALLOWED_DOMAINS = new HashSet<>(Arrays.asList(
      "www.youtube.com",
      "youtube.com",
      "www.youtube-nocookie.com",
      "player.vimeo.com"
  ));

  private static final Set<String> ALLOWED_ATTRIBUTES = new HashSet<>(Arrays.asList(
      "src", "style", "allow", "allowfullscreen", "referrerpolicy",
      "width", "height", "frameborder"
  ));

  // Matches an iframe tag with its content
  private static final Pattern IFRAME_PATTERN = Pattern.compile(
      "<iframe\\s+([^>]*)>\\s*</iframe>",
      Pattern.CASE_INSENSITIVE | Pattern.DOTALL
  );

  // Matches individual attributes: name="value" or name (boolean)
  private static final Pattern ATTR_PATTERN = Pattern.compile(
      "([a-zA-Z][a-zA-Z0-9-]*)(?:\\s*=\\s*\"([^\"]*)\")?",
      Pattern.CASE_INSENSITIVE
  );

  // Detects script tags
  private static final Pattern SCRIPT_PATTERN = Pattern.compile(
      "<script[^>]*>.*?</script>|<script[^>]*/>",
      Pattern.CASE_INSENSITIVE | Pattern.DOTALL
  );

  // Detects event handler attributes
  private static final Pattern EVENT_HANDLER_PATTERN = Pattern.compile(
      "\\bon[a-z]+\\s*=",
      Pattern.CASE_INSENSITIVE
  );

  // Detects javascript: or data: URIs
  private static final Pattern DANGEROUS_URI_PATTERN = Pattern.compile(
      "(?:javascript|data)\\s*:",
      Pattern.CASE_INSENSITIVE
  );

  /**
   * Sanitizes embed HTML. Returns sanitized iframe HTML if valid, or null if the
   * input fails any safety check.
   *
   * @param html The embed HTML to sanitize. May be null.
   * @return Sanitized iframe HTML, or null if input is unsafe or invalid.
   */
  @Nullable
  public static String sanitize(@Nullable String html) {
    if (html == null || html.trim().isEmpty()) {
      return null;
    }

    String trimmed = html.trim();

    // Reject anything containing script tags
    if (SCRIPT_PATTERN.matcher(trimmed).find()) {
      return null;
    }

    // Reject anything with event handlers
    if (EVENT_HANDLER_PATTERN.matcher(trimmed).find()) {
      return null;
    }

    // Reject anything with dangerous URI schemes
    if (DANGEROUS_URI_PATTERN.matcher(trimmed).find()) {
      return null;
    }

    // Must be a single iframe tag
    Matcher iframeMatcher = IFRAME_PATTERN.matcher(trimmed);
    if (!iframeMatcher.matches()) {
      return null;
    }

    String attributesStr = iframeMatcher.group(1);

    // Parse and validate attributes
    String srcValue = null;
    StringBuilder sanitizedAttrs = new StringBuilder();

    Matcher attrMatcher = ATTR_PATTERN.matcher(attributesStr);
    while (attrMatcher.find()) {
      String attrName = attrMatcher.group(1).toLowerCase(Locale.ROOT);
      String attrValue = attrMatcher.group(2);

      if (!ALLOWED_ATTRIBUTES.contains(attrName)) {
        // Skip disallowed attributes silently
        continue;
      }

      if ("src".equals(attrName)) {
        if (attrValue == null) {
          return null;
        }
        srcValue = attrValue;

        // Validate src scheme — must be https://
        if (!attrValue.startsWith("https://")) {
          return null;
        }

        // Validate domain against allowlist
        if (!isDomainAllowed(attrValue)) {
          return null;
        }
      }

      if (sanitizedAttrs.length() > 0) {
        sanitizedAttrs.append(' ');
      }

      if (attrValue != null) {
        sanitizedAttrs.append(attrName).append("=\"").append(attrValue).append('"');
      } else {
        sanitizedAttrs.append(attrName);
      }
    }

    // src is required
    if (srcValue == null) {
      return null;
    }

    return "<iframe " + sanitizedAttrs.toString() + "></iframe>";
  }

  private static boolean isDomainAllowed(@Nonnull String url) {
    // Extract domain from URL: https://domain/path
    String withoutScheme = url.substring("https://".length());
    int slashIndex = withoutScheme.indexOf('/');
    String domain;
    if (slashIndex > 0) {
      domain = withoutScheme.substring(0, slashIndex);
    } else {
      domain = withoutScheme;
    }
    // Remove port if present
    int portIndex = domain.indexOf(':');
    if (portIndex > 0) {
      domain = domain.substring(0, portIndex);
    }
    return ALLOWED_DOMAINS.contains(domain.toLowerCase(Locale.ROOT));
  }
}
