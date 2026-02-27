package io.kestros.cms.components.basic.core.content.heading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class HeadingDataSourceComponentTest extends BaseDataSourceComponentTest {

  private HeadingDataSourceComponent heading;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosHeading.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);

  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {

  }

  @Test
  public void testGetHeadingText() {
    properties.put("kes:datasource", "default");
    properties.put("headingText", "Sample Heading");
    resource = context.create().resource("/content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("Sample Heading", heading.getHeadingText());
  }

  @Test
  public void testGetHeadingType() {
    properties.put("kes:datasource", "default");
    properties.put("headingType", "h1");
    resource = context.create().resource("/content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("h1", heading.getHeadingType());
  }


  @Test
  public void testGetHeadingTextWhenPageTitle() {
    Map<String,Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    Map<String,Object> pageJcrContentProperties = new HashMap<>();
    pageJcrContentProperties.put("jcr:title", "PageTitle");
    context.create().resource("/content/page", pageProperties);
    context.create().resource("/content/page/jcr:content", pageJcrContentProperties);

    properties.put("headingText", "Sample Heading");
    properties.put("kes:datasource", "page-title");
    resource = context.create().resource("/content/page/jcr:content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("PageTitle", heading.getHeadingText());
  }

  @Test
  public void testGetHeadingTypeWhenPageTitle() {
    properties.put("kes:datasource", "page-title");
    properties.put("headingType", "h1");
    resource = context.create().resource("/content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("h1", heading.getHeadingType());
  }

  @Test
  public void testGetComponentResourceType() {
    properties.put("kes:datasource", "default");
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);
    resource = context.create().resource("/content/heading-2", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals(KestrosHeading.RESOURCE_TYPE, heading.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    properties.put("kes:datasource", "default");
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);
    resource = context.create().resource("/content/heading-3", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertNotNull(heading.getResource());
  }

  @Test
  public void testGetPath() {
    properties.put("kes:datasource", "default");
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);
    resource = context.create().resource("/content/heading-4", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertNotNull(heading.getPath());
  }

}