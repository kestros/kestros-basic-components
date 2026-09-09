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
}
