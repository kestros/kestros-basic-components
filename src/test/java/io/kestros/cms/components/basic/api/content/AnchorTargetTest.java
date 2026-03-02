package io.kestros.cms.components.basic.api.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Rule;
import org.junit.Test;

public class AnchorTargetTest {

  @Rule
  public SlingContext context = new SlingContext();

  @Test
  public void testGetTargetValueSameWindow() {
    assertEquals("_self", AnchorTarget.SAME_WINDOW.getTargetValue());
  }

  @Test
  public void testGetTargetValueNewWindow() {
    assertEquals("_blank", AnchorTarget.NEW_WINDOW.getTargetValue());
  }

  @Test
  public void testLookupByStringSelf() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup("_self"));
  }

  @Test
  public void testLookupByStringBlank() {
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup("_blank"));
  }

  @Test
  public void testLookupByStringCaseInsensitive() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup("_SELF"));
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup("_BLANK"));
  }

  @Test
  public void testLookupByStringNull() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup((String) null));
  }

  @Test
  public void testLookupByStringUnknown() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup("unknown"));
  }

  @Test
  public void testLookupByBooleanTrue() {
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup(Boolean.TRUE));
  }

  @Test
  public void testLookupByBooleanFalse() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup(Boolean.FALSE));
  }

  @Test
  public void testLookupByBooleanNull() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup((Boolean) null));
  }

  @Test
  public void testLookupByResourceWithTargetProperty() {
    Map<String, Object> props = new HashMap<>();
    props.put("target", "_blank");
    Resource resource = context.create().resource("/content/link-1", props);
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup(resource));
  }

  @Test
  public void testLookupByResourceWithOpenInNewTabTrue() {
    Map<String, Object> props = new HashMap<>();
    props.put("openInNewTab", true);
    Resource resource = context.create().resource("/content/link-2", props);
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup(resource));
  }

  @Test
  public void testLookupByResourceWithOpenInNewTabFalse() {
    Map<String, Object> props = new HashMap<>();
    props.put("openInNewTab", false);
    Resource resource = context.create().resource("/content/link-3", props);
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup(resource));
  }

  @Test
  public void testLookupByResourceNull() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup((Resource) null));
  }

  @Test
  public void testLookupByResourceNoProperties() {
    Resource resource = context.create().resource("/content/link-4");
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup(resource));
  }

  @Test
  public void testValues() {
    assertNotNull(AnchorTarget.values());
    assertEquals(2, AnchorTarget.values().length);
  }
}
