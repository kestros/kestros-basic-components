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

package io.kestros.cms.components.basic.core.content.heading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.kestros.cms.components.basic.api.exceptions.ComponentElementRenderingException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class HeadingPageTitleDataSourceTest extends BaseDataSourceTest {

  private final Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    final Map<String, Object> contentProperties = new HashMap<>();
    contentProperties.put("jcr:primaryType", "nt:unstructured");
    contentProperties.put("jcr:title", "The Page Title");
    context.create().resource("/content/page", pageProperties);
    context.create().resource("/content/page/jcr:content", contentProperties);
  }

  @Override
  public void testToSyntheticResource() {
    final Resource resource =
        context.create().resource("/content/page/jcr:content/heading", properties);
    context.request().setResource(resource);

    assertNotNull(context.request().adaptTo(HeadingPageTitleDataSource.class)
        .toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content"));
  }

  @Test
  public void testGetHeadingTextComesFromTheContainingPage() {
    final Resource resource =
        context.create().resource("/content/page/jcr:content/heading-inherited", properties);
    context.request().setResource(resource);

    assertEquals("The Page Title",
        context.request().adaptTo(HeadingPageTitleDataSource.class).getHeadingText());
  }

  /**
   * Overriding the inherited title needs the current page, which only a request can supply. This
   * used to throw a bare RuntimeException; it now names the component that could not resolve.
   */
  @Test
  public void testOverrideInheritedTitleWithoutARequestNamesTheComponent() {
    properties.put("overrideInheritedTitle", true);
    final Resource resource =
        context.create().resource("/content/page/jcr:content/heading-override", properties);

    try {
      resource.adaptTo(HeadingPageTitleDataSource.class).getPage();
      fail("Expected a ComponentElementRenderingException.");
    } catch (final ComponentElementRenderingException e) {
      assertTrue(e.getMessage().contains("/content/page/jcr:content/heading-override"));
      assertTrue(e.getMessage().contains("overrideInheritedTitle"));
    }
  }

  @Test
  public void testIsOverrideInheritedTitleDefaultsToFalse() {
    final Resource resource =
        context.create().resource("/content/page/jcr:content/heading-default", properties);
    context.request().setResource(resource);

    assertEquals(Boolean.FALSE,
        context.request().adaptTo(HeadingPageTitleDataSource.class).isOverrideInheritedTitle());
  }
}
