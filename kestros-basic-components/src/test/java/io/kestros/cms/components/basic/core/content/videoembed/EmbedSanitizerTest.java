package io.kestros.cms.components.basic.core.content.videoembed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmbedSanitizerTest {

  @Test
  public void testValidYouTubeIframePasses() {
    String input = "<iframe src=\"https://www.youtube.com/embed/zzzzzzzzzzz\" "
        + "style=\"width:100%;height:100%;border:0;\" allowfullscreen "
        + "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; "
        + "picture-in-picture; fullscreen\" "
        + "referrerpolicy=\"strict-origin-when-cross-origin\"></iframe>";

    String result = EmbedSanitizer.sanitize(input);

    assertNotNull("Valid YouTube iframe should pass sanitization", result);
    assertTrue("Result should contain YouTube URL",
        result.contains("https://www.youtube.com/embed/zzzzzzzzzzz"));
    assertTrue("Result should be an iframe", result.startsWith("<iframe "));
    assertTrue("Result should end with closing iframe", result.endsWith("</iframe>"));
  }

  @Test
  public void testScriptTagReturnsNull() {
    String input = "<script>alert(1)</script>";

    assertNull("Script tag should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testScriptInjectionInIframeReturnsNull() {
    String input = "<script>alert(1)</script>"
        + "<iframe src=\"https://www.youtube.com/embed/test\"></iframe>";

    assertNull("Script injection alongside iframe should be rejected",
        EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testJavascriptSrcReturnsNull() {
    String input = "<iframe src=\"javascript:alert(1)\"></iframe>";

    assertNull("javascript: src should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testDataSrcReturnsNull() {
    String input = "<iframe src=\"data:text/html,<script>alert(1)</script>\"></iframe>";

    assertNull("data: src should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testOnEventHandlersStripped() {
    String input = "<iframe src=\"https://www.youtube.com/embed/test\" "
        + "onload=\"alert(1)\"></iframe>";

    assertNull("Event handlers should cause rejection", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testNonAllowlistedDomainReturnsNull() {
    String input = "<iframe src=\"https://evil.com/embed\"></iframe>";

    assertNull("Non-allowlisted domain should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testNullInputReturnsNull() {
    assertNull("Null input should return null", EmbedSanitizer.sanitize(null));
  }

  @Test
  public void testEmptyStringReturnsNull() {
    assertNull("Empty string should return null", EmbedSanitizer.sanitize(""));
  }

  @Test
  public void testPlainTextReturnsNull() {
    assertNull("Plain text should return null", EmbedSanitizer.sanitize("just some text"));
  }

  @Test
  public void testHttpSrcReturnsNull() {
    String input = "<iframe src=\"http://www.youtube.com/embed/test\"></iframe>";

    assertNull("HTTP (non-HTTPS) src should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testVimeoIframePasses() {
    String input = "<iframe src=\"https://player.vimeo.com/video/123456\" "
        + "width=\"640\" height=\"360\" frameborder=\"0\" allowfullscreen></iframe>";

    String result = EmbedSanitizer.sanitize(input);

    assertNotNull("Valid Vimeo iframe should pass", result);
    assertTrue("Result should contain Vimeo URL",
        result.contains("https://player.vimeo.com/video/123456"));
  }

  @Test
  public void testYouTubeNoCookieIframePasses() {
    String input = "<iframe src=\"https://www.youtube-nocookie.com/embed/test\" "
        + "allowfullscreen></iframe>";

    String result = EmbedSanitizer.sanitize(input);

    assertNotNull("youtube-nocookie.com iframe should pass", result);
    assertTrue("Result should contain youtube-nocookie URL",
        result.contains("https://www.youtube-nocookie.com/embed/test"));
  }

  @Test
  public void testNoSrcAttributeReturnsNull() {
    String input = "<iframe style=\"width:100%\"></iframe>";

    assertNull("Iframe without src should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testDivTagReturnsNull() {
    String input = "<div>not an iframe</div>";

    assertNull("Non-iframe tags should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testImgXssReturnsNull() {
    String input = "<img src=x onerror=alert(1)>";

    assertNull("img XSS should be rejected", EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testSvgXssReturnsNull() {
    String input = "<svg/onload=alert(1)>";

    assertNull("SVG XSS should be rejected", EmbedSanitizer.sanitize(input));
  }

  /**
   * A host that is not ASCII must be rejected outright, not case-folded into the allow-list.
   * "www.youtube-nocooKie.com" below uses U+212A KELVIN SIGN in place of the 'k'. Java's
   * toLowerCase folds it to 'k', so the previous implementation accepted this host as
   * www.youtube-nocookie.com. This test fails against that implementation.
   */
  @Test
  public void testNonAsciiHostFoldingOntoAllowedDomainIsRejected() {
    String input = "<iframe src=\"https://www.youtube-nocoo\u212Aie.com/embed/abc\"></iframe>";

    assertNull("A host containing U+212A must not fold onto www.youtube-nocookie.com",
        EmbedSanitizer.sanitize(input));
  }

  @Test
  public void testAsciiHostCaseIsStillAccepted() {
    String input = "<iframe src=\"https://WWW.YouTube.com/embed/abc\"></iframe>";

    assertNotNull("ASCII case variation of an allowed host must still be accepted",
        EmbedSanitizer.sanitize(input));
  }
}
