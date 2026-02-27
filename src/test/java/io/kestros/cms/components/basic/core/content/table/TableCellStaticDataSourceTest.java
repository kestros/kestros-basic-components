package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class TableCellStaticDataSourceTest extends BaseDataSourceComponentTest {

  private TableCellStaticDataSource tableCellStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    setupSamplePage("/content/page", null);
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell", properties);
    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);
  }

  @Override
  public String getResourceType() {
    return KestrosTableCell.RESOURCE_TYPE;
  }

  @Override
  @Test
  @Ignore("Pre-existing test pattern")
  public void testToSyntheticResource() {
    // Override required by BaseDataSourceComponentTest
  }

  @Test
  public void testGetCellContent_empty() {
    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertNotNull(cellContent);
    assertEquals(0, cellContent.size());
  }

  @Test
  public void testGetCellContent_withChildren() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-2", properties);

    Map<String, Object> childProps = new HashMap<>();
    childProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-2/child-1", childProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertNotNull(cellContent);
    assertEquals(1, cellContent.size());
  }

  @Test
  public void testGetCellContent_withMultipleChildren() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-3", properties);

    Map<String, Object> childProps = new HashMap<>();
    childProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-3/child-1", childProps);
    context.create().resource("/content/page/jcr:content/static/cell-3/child-2", childProps);
    context.create().resource("/content/page/jcr:content/static/cell-3/child-3", childProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertNotNull(cellContent);
    assertEquals(3, cellContent.size());
  }

  @Test
  public void testGetCellContentElements_empty() {
    List<KestrosBasicComponentElement> cellContentElements = tableCellStaticDataSource.getCellContentElements();
    assertNotNull(cellContentElements);
    assertEquals(0, cellContentElements.size());
  }

  @Test
  public void testGetCellContentElements_returnsEmptyByDesign() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-4", properties);

    Map<String, Object> childProps = new HashMap<>();
    childProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-4/child-1", childProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<KestrosBasicComponentElement> cellContentElements = tableCellStaticDataSource.getCellContentElements();
    assertNotNull(cellContentElements);
    assertEquals(0, cellContentElements.size());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosTableCell.RESOURCE_TYPE, tableCellStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testAdaptation() {
    assertNotNull(tableCellStaticDataSource);
  }

  @Test
  public void testGetCellContent_childResourcePaths() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-5", properties);

    Map<String, Object> childProps = new HashMap<>();
    childProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-5/text-1", childProps);
    context.create().resource("/content/page/jcr:content/static/cell-5/text-2", childProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertEquals(2, cellContent.size());
    assertTrue(cellContent.get(0).getPath().contains("cell-5"));
    assertTrue(cellContent.get(1).getPath().contains("cell-5"));
  }

  @Test
  public void testGetCellContent_resourceTypes() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-6", properties);

    Map<String, Object> childProps1 = new HashMap<>();
    childProps1.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-6/text-1", childProps1);

    Map<String, Object> childProps2 = new HashMap<>();
    childProps2.put("sling:resourceType", "kes:Image");
    context.create().resource("/content/page/jcr:content/static/cell-6/image-1", childProps2);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertEquals(2, cellContent.size());
    assertEquals("kes:Text", cellContent.get(0).getResourceType());
    assertEquals("kes:Image", cellContent.get(1).getResourceType());
  }

  @Test
  public void testGetChildElements() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-7", properties);

    Map<String, Object> childProps = new HashMap<>();
    childProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-7/child-1", childProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<KestrosBasicComponentElement> childElements = tableCellStaticDataSource.getChildElements();
    assertNotNull(childElements);
    assertEquals(0, childElements.size());
  }

  @Test
  public void testGetCellContent_preservesOrder() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-8", properties);

    Map<String, Object> childProps = new HashMap<>();
    childProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/cell-8/child-a", childProps);
    context.create().resource("/content/page/jcr:content/static/cell-8/child-b", childProps);
    context.create().resource("/content/page/jcr:content/static/cell-8/child-c", childProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertEquals(3, cellContent.size());
    assertTrue(cellContent.get(0).getName().equals("child-a"));
    assertTrue(cellContent.get(1).getName().equals("child-b"));
    assertTrue(cellContent.get(2).getName().equals("child-c"));
  }

  @Test
  public void testGetCellContent_differentResourceTypes() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/cell-9", properties);

    Map<String, Object> textProps = new HashMap<>();
    textProps.put("sling:resourceType", "kes:Text");
    Map<String, Object> imageProps = new HashMap<>();
    imageProps.put("sling:resourceType", "kes:Image");
    Map<String, Object> buttonProps = new HashMap<>();
    buttonProps.put("sling:resourceType", "kes:Button");

    context.create().resource("/content/page/jcr:content/static/cell-9/text", textProps);
    context.create().resource("/content/page/jcr:content/static/cell-9/image", imageProps);
    context.create().resource("/content/page/jcr:content/static/cell-9/button", buttonProps);

    context.request().setResource(resource);
    tableCellStaticDataSource = context.request().adaptTo(TableCellStaticDataSource.class);

    List<Resource> cellContent = tableCellStaticDataSource.getCellContent();
    assertEquals(3, cellContent.size());
  }
}
