package io.kestros.cms.components.basic.api.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TextElementTypeTest {

  @Test
  public void testValuesNotNull() {
    assertNotNull(TextElementType.values());
  }

  @Test
  public void testValuesCount() {
    assertEquals(3, TextElementType.values().length);
  }

  @Test
  public void testParagraphDisplayText() {
    assertEquals("Paragraph", TextElementType.PARAGRAPH.getDisplayText());
  }

  @Test
  public void testParagraphValue() {
    assertEquals("paragraph", TextElementType.PARAGRAPH.getValue());
  }

  @Test
  public void testPreformattedDisplayText() {
    assertEquals("Preformatted", TextElementType.PREFORMATTED.getDisplayText());
  }

  @Test
  public void testPreformattedValue() {
    assertEquals("preformatted", TextElementType.PREFORMATTED.getValue());
  }

  @Test
  public void testBlockquoteDisplayText() {
    assertEquals("Blockquote", TextElementType.BLOCKQUOTE.getDisplayText());
  }

  @Test
  public void testBlockquoteValue() {
    assertEquals("blockquote", TextElementType.BLOCKQUOTE.getValue());
  }

  @Test
  public void testValueOf() {
    assertEquals(TextElementType.PARAGRAPH, TextElementType.valueOf("PARAGRAPH"));
    assertEquals(TextElementType.PREFORMATTED, TextElementType.valueOf("PREFORMATTED"));
    assertEquals(TextElementType.BLOCKQUOTE, TextElementType.valueOf("BLOCKQUOTE"));
  }
}
