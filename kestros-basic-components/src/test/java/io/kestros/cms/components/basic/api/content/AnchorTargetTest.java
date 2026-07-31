package io.kestros.cms.components.basic.api.content;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Rule;
import org.junit.Test;

public class AnchorTargetTest {

  @Rule
  public SlingContext context = new SlingContext();

  @Test
  public void testGetTargetValue() {
    assertEquals("_self", AnchorTarget.SAME_WINDOW.getTargetValue());
    assertEquals("_blank", AnchorTarget.NEW_WINDOW.getTargetValue());
  }

  @Test
  public void testLookupByStringValue() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup("_self"));
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup("_blank"));
  }

  @Test
  public void testLookupByStringValueIsCaseInsensitive() {
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup("_BLANK"));
  }

  @Test
  public void testLookupByStringValueFallsBackToSameWindow() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup("_parent"));
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup((String) null));
  }

  @Test
  public void testLookupByBoolean() {
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.lookup(Boolean.TRUE));
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup(Boolean.FALSE));
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup((Boolean) null));
  }

  @Test
  public void testLookupByResourceUsingTheTargetProperty() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("target", "_blank");
    context.create().resource("/content/link-target", properties);

    assertEquals(AnchorTarget.NEW_WINDOW,
        AnchorTarget.lookup(context.resourceResolver().getResource("/content/link-target")));
  }

  /** With no target property, the openInNewTab flag decides. */
  @Test
  public void testLookupByResourceFallsBackToOpenInNewTab() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("openInNewTab", true);
    context.create().resource("/content/link-new-tab", properties);

    assertEquals(AnchorTarget.NEW_WINDOW,
        AnchorTarget.lookup(context.resourceResolver().getResource("/content/link-new-tab")));
  }

  @Test
  public void testLookupByResourceWithNeitherProperty() {
    context.create().resource("/content/link-plain", new HashMap<>());

    assertEquals(AnchorTarget.SAME_WINDOW,
        AnchorTarget.lookup(context.resourceResolver().getResource("/content/link-plain")));
  }

  @Test
  public void testLookupByResourceWhenTheResourceIsNull() {
    assertEquals(AnchorTarget.SAME_WINDOW, AnchorTarget.lookup((org.apache.sling.api.resource.Resource) null));
  }

  @Test
  public void testValueOf() {
    assertEquals(AnchorTarget.NEW_WINDOW, AnchorTarget.valueOf("NEW_WINDOW"));
    assertEquals(2, AnchorTarget.values().length);
  }
}
