package io.kestros.cms.components.basic.api.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class HeadingLevelsTest {

  @Test
  public void testLookup() {
    assertEquals(HeadingLevels.H1, HeadingLevels.lookup("h1"));
    assertEquals(HeadingLevels.H2, HeadingLevels.lookup("h2"));
    assertEquals(HeadingLevels.H3, HeadingLevels.lookup("h3"));
    assertEquals(HeadingLevels.H4, HeadingLevels.lookup("h4"));
    assertEquals(HeadingLevels.H5, HeadingLevels.lookup("h5"));
    assertEquals(HeadingLevels.H6, HeadingLevels.lookup("h6"));
    assertNull(HeadingLevels.lookup("h7"));
  }

  @Test
  public void testGetDisplayText() {
    assertEquals("Heading 1", HeadingLevels.H1.getDisplayText());
    assertEquals("Heading 2", HeadingLevels.H2.getDisplayText());
    assertEquals("Heading 3", HeadingLevels.H3.getDisplayText());
    assertEquals("Heading 4", HeadingLevels.H4.getDisplayText());
    assertEquals("Heading 5", HeadingLevels.H5.getDisplayText());
    assertEquals("Heading 6", HeadingLevels.H6.getDisplayText());
  }

  @Test
  public void testGetValue() {
    assertEquals("h1", HeadingLevels.H1.getValue());
    assertEquals("h2", HeadingLevels.H2.getValue());
    assertEquals("h3", HeadingLevels.H3.getValue());
    assertEquals("h4", HeadingLevels.H4.getValue());
    assertEquals("h5", HeadingLevels.H5.getValue());
    assertEquals("h6", HeadingLevels.H6.getValue());
  }
}