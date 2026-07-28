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

/**
 * Replaced an anonymous SyntheticResource subclass that both synthetic-resource builders declared
 * inline.
 */
public class PropertyBackedSyntheticResourceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private ResourceMetadata metadata;
  private Map<String, Object> properties;

  @Before
  public void setUp() {
    metadata = new ResourceMetadata();
    metadata.setResolutionPath("/synthetics/content/page/element");
    properties = new HashMap<>();
    properties.put("jcr:title", "A title");
  }

  @Test
  public void testGetValueMapExposesTheSuppliedProperties() {
    final PropertyBackedSyntheticResource resource = new PropertyBackedSyntheticResource(
        context.resourceResolver(), metadata, "kestros/components/basic/text", properties);

    assertEquals("A title", resource.getValueMap().get("jcr:title", String.class));
  }

  @Test
  public void testGetResourceType() {
    final PropertyBackedSyntheticResource resource = new PropertyBackedSyntheticResource(
        context.resourceResolver(), metadata, "kestros/components/basic/text", properties);

    assertEquals("kestros/components/basic/text", resource.getResourceType());
  }

  /**
   * The properties are copied on the way in, so a caller changing its own map afterwards cannot
   * rewrite what the resource reports.
   */
  @Test
  public void testTheSuppliedPropertiesAreCopied() {
    final PropertyBackedSyntheticResource resource = new PropertyBackedSyntheticResource(
        context.resourceResolver(), metadata, "kestros/components/basic/text", properties);

    properties.put("jcr:title", "Changed after construction");
    properties.put("added", "later");

    assertEquals("A title", resource.getValueMap().get("jcr:title", String.class));
    assertNull(resource.getValueMap().get("added", String.class));
  }

  @Test
  public void testEmptyProperties() {
    final PropertyBackedSyntheticResource resource = new PropertyBackedSyntheticResource(
        context.resourceResolver(), metadata, "kestros/components/basic/text", new HashMap<>());

    assertNull(resource.getValueMap().get("jcr:title", String.class));
  }
}
