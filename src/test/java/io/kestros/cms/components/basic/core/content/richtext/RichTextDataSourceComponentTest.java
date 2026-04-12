package io.kestros.cms.components.basic.core.content.richtext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.core.TextSanitizer;
import org.junit.Test;

public class RichTextDataSourceComponentTest {

  @Test
  public void testNull() {
    assertNull(TextSanitizer.escapeMultiByteToEntities(null));
  }

  @Test
  public void testEmpty() {
    assertEquals("", TextSanitizer.escapeMultiByteToEntities(""));
  }

  @Test
  public void testAsciiPassesThrough() {
    assertEquals("Hello world", TextSanitizer.escapeMultiByteToEntities("Hello world"));
  }

  @Test
  public void testHtmlPassesThrough() {
    String html = "<p>Hello <strong>world</strong></p>";
    assertEquals(html, TextSanitizer.escapeMultiByteToEntities(html));
  }

  @Test
  public void testEmDash() {
    assertEquals("Goals &#8212; Assists",
        TextSanitizer.escapeMultiByteToEntities("Goals \u2014 Assists"));
  }

  @Test
  public void testTwoEmDashes() {
    assertEquals("&#8212;&#8212;",
        TextSanitizer.escapeMultiByteToEntities("\u2014\u2014"));
  }

  @Test
  public void testCurlyQuotes() {
    assertEquals("&#8220;Hello&#8221;",
        TextSanitizer.escapeMultiByteToEntities("\u201CHello\u201D"));
  }

  @Test
  public void testRightArrow() {
    assertEquals("A &#8594; B",
        TextSanitizer.escapeMultiByteToEntities("A \u2192 B"));
  }

  @Test
  public void testCurlyApostrophe() {
    assertEquals("What&#8217;s next",
        TextSanitizer.escapeMultiByteToEntities("What\u2019s next"));
  }

  @Test
  public void testMixedHtmlAndUnicode() {
    assertEquals("<p>Goals &#8212; &#8220;Assists&#8221;</p>",
        TextSanitizer.escapeMultiByteToEntities("<p>Goals \u2014 \u201CAssists\u201D</p>"));
  }

  @Test
  public void testHtmlEntitiesUntouched() {
    String html = "<p>Goals &mdash; Assists</p>";
    assertEquals(html, TextSanitizer.escapeMultiByteToEntities(html));
  }

  @Test
  public void testCopyright() {
    assertEquals("&#169; 2026",
        TextSanitizer.escapeMultiByteToEntities("\u00A9 2026"));
  }
}
