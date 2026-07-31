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
