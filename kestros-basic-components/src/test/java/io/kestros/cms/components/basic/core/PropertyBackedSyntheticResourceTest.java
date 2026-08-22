package io.kestros.cms.components.basic.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PropertyBackedSyntheticResourceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private Map<String, Object> properties;
  private PropertyBackedSyntheticResource resource;

  @Before
  public void setUp() {
    properties = new HashMap<>();
    properties.put("heading", "Original Heading");
    resource = new PropertyBackedSyntheticResource(context.resourceResolver(),
        new ResourceMetadata(), "kestros/components/heading", properties);
  }

  @Test
  public void testGetValueMap() {
    assertEquals("Original Heading", resource.getValueMap().get("heading", String.class));
  }

  @Test
  public void testGetValueMapWhenCallerWritesToTheMapItWasGiven() {
    resource.getValueMap().put("heading", "Rewritten Heading");

    assertEquals("Original Heading", resource.getValueMap().get("heading", String.class));
  }

  @Test
  public void testGetValueMapWhenCallerRemovesFromTheMapItWasGiven() {
    resource.getValueMap().remove("heading");

    assertEquals("Original Heading", resource.getValueMap().get("heading", String.class));
  }

  @Test
  public void testGetValueMapWhenTheSuppliedMapChangesAfterConstruction() {
    properties.put("heading", "Rewritten Heading");

    assertEquals("Original Heading", resource.getValueMap().get("heading", String.class));
  }

  @Test
  public void testGetValueMapWhenPropertyIsAbsent() {
    assertNull(resource.getValueMap().get("missing", String.class));
  }
}
