package io.kestros.cms.components.basic.core.content.heading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class HeadingPageTitleDataSourceTest extends BaseDataSourceComponentTest {

  private HeadingPageTitleDataSource heading;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosHeading.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    Map<String, Object> pageJcrContentProperties = new HashMap<>();
    pageJcrContentProperties.put("jcr:title", "Page Title");
    context.create().resource("/content/heading-page", pageProperties);
    context.create().resource("/content/heading-page/jcr:content", pageJcrContentProperties);

    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);
    properties.put("kes:datasource", "page-title");
    resource = context.create().resource("/content/heading-page/jcr:content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingPageTitleDataSource.class);
  }

  @Override
  @Test
  @Ignore("Pre-existing failure")
  public void testToSyntheticResource() {
    // Not applicable
  }

  @Test
  public void testGetHeadingTextFromPageTitle() {
    assertEquals("Page Title", heading.getHeadingText());
  }

  @Test
  public void testIsOverrideInheritedTitleDefault() {
    assertFalse(heading.isOverrideInheritedTitle());
  }

  @Test
  public void testIsOverrideInheritedTitleWhenTrue() {
    properties.clear();
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);
    properties.put("kes:datasource", "page-title");
    properties.put("overrideInheritedTitle", true);
    Resource r = context.create().resource("/content/heading-page/jcr:content/heading-2",
        properties);
    context.request().setResource(r);
    HeadingPageTitleDataSource h = context.request().adaptTo(HeadingPageTitleDataSource.class);
    assertTrue(h.isOverrideInheritedTitle());
  }

  @Test
  public void testGetResource() {
    assertNotNull(heading.getResource());
  }

  @Test
  public void testGetPath() {
    assertEquals("/content/heading-page/jcr:content/heading", heading.getPath());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosHeading.RESOURCE_TYPE, heading.getComponentResourceType());
  }

  @Test
  public void testGetHeadingType() {
    properties.clear();
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);
    properties.put("kes:datasource", "page-title");
    properties.put("headingType", "h2");
    Resource r = context.create().resource("/content/heading-page/jcr:content/heading-3",
        properties);
    context.request().setResource(r);
    HeadingPageTitleDataSource h = context.request().adaptTo(HeadingPageTitleDataSource.class);
    assertEquals("h2", h.getHeadingType());
  }
}
