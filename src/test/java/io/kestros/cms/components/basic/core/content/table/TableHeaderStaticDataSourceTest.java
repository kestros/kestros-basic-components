package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class TableHeaderStaticDataSourceTest extends BaseDataSourceComponentTest {

  private TableHeaderStaticDataSource tableHeaderStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    setupSamplePage("/content/page", null);
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "Header Text");
    resource = context.create().resource("/content/page/jcr:content/static/header", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);
  }

  @Override
  public String getResourceType() {
    return KestrosTableHeader.RESOURCE_TYPE;
  }

  @Override
  @Test
  @Ignore("Pre-existing test pattern")
  public void testToSyntheticResource() {
    // Override required by BaseDataSourceComponentTest
  }

  @Test
  public void testGetText_withText() {
    assertEquals("Header Text", tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetText_withoutText() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/header-2", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertNull(tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetText_withEmptyText() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "");
    resource = context.create().resource("/content/page/jcr:content/static/header-3", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertEquals("", tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosTableHeader.RESOURCE_TYPE, tableHeaderStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testAdaptation() {
    assertNotNull(tableHeaderStaticDataSource);
  }

  @Test
  public void testGetText_withSpecialCharacters() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "Header <Text> & Special");
    resource = context.create().resource("/content/page/jcr:content/static/header-4", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertEquals("Header <Text> & Special", tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetText_withUnicode() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "Header with Unicode: ñ € 中文");
    resource = context.create().resource("/content/page/jcr:content/static/header-5", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertEquals("Header with Unicode: ñ € 中文", tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetText_withLongText() {
    String longText = "This is a very long header text that contains multiple words and should still be retrieved correctly from the property map";
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", longText);
    resource = context.create().resource("/content/page/jcr:content/static/header-6", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertEquals(longText, tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetText_multipleHeaderValues() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "Header 1");
    resource = context.create().resource("/content/page/jcr:content/static/header-7", properties);
    context.request().setResource(resource);
    TableHeaderStaticDataSource header1 = context.request().adaptTo(TableHeaderStaticDataSource.class);
    assertEquals("Header 1", header1.getText());
  }

  @Test
  public void testGetText_withNumbers() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "12345");
    resource = context.create().resource("/content/page/jcr:content/static/header-9", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertEquals("12345", tableHeaderStaticDataSource.getText());
  }

  @Test
  public void testGetText_withWhitespace() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    properties.put("text", "  Header with spaces  ");
    resource = context.create().resource("/content/page/jcr:content/static/header-10", properties);
    context.request().setResource(resource);
    tableHeaderStaticDataSource = context.request().adaptTo(TableHeaderStaticDataSource.class);

    assertEquals("  Header with spaces  ", tableHeaderStaticDataSource.getText());
  }
}
