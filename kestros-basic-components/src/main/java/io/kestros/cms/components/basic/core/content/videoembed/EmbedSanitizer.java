package io.kestros.cms.components.basic.core.content.videoembed;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
      String attrName = toAsciiLowerCase(attrMatcher.group(1));
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
    // A host outside ASCII is rejected outright rather than case-folded into the allow-list.
    // Unicode case folding maps non-ASCII characters onto ASCII ones - U+212A KELVIN SIGN
    // lowercases to 'k', which is enough to turn an attacker-supplied host into a spelling of
    // www.youtube-nocookie.com that Java's Set.contains accepts. Browsers happen to apply UTS46
    // and fold the same way, so this was not exploitable, but that is a property of the browser
    // rather than of this method, and a sanitizer should not be leaning on it. Every domain in
    // the allow-list is ASCII, so nothing legitimate is turned away.
    if (!isAscii(domain)) {
      return false;
    }
    return ALLOWED_DOMAINS.contains(toAsciiLowerCase(domain));
  }

  /**
   * Whether every character is ASCII.
   *
   * @param value The value to check.
   *
   * @return True if the value contains no character above U+007F.
   */
  private static boolean isAscii(@Nonnull String value) {
    int length = value.length();
    for (int i = 0; i < length; i++) {
      if (value.charAt(i) > 0x7F) {
        return false;
      }
    }
    return true;
  }

  /**
   * Lowercases the ASCII letters A-Z and leaves every other character untouched, so that no
   * character outside ASCII can be folded onto one inside it.
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
}
