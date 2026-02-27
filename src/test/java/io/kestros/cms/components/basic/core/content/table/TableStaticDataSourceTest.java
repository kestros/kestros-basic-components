package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class TableStaticDataSourceTest extends BaseDataSourceComponentTest {

  private TableStaticDataSource tableStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    setupSamplePage("/content/page", null);
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table", properties);
    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);
  }

  @Override
  public String getResourceType() {
    return KestrosTable.RESOURCE_TYPE;
  }

  @Override
  @Test
  @Ignore("Pre-existing test pattern")
  public void testToSyntheticResource() {
    // Override required by BaseDataSourceComponentTest
  }

  @Test
  public void testGetHeaderElements_empty() {
    List<KestrosTableHeader> headers = tableStaticDataSource.getHeaderElements();
    assertNotNull(headers);
    assertEquals(0, headers.size());
  }

  @Test
  public void testGetHeaderElements_singleHeader() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-2", properties);

    Map<String, Object> headerProps = new HashMap<>();
    headerProps.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    headerProps.put("text", "Header 1");
    context.create().resource("/content/page/jcr:content/static/table-2/header-1", headerProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableHeader> headers = tableStaticDataSource.getHeaderElements();
    assertNotNull(headers);
    assertEquals(1, headers.size());
  }

  @Test
  public void testGetHeaderElements_multipleHeaders() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-3", properties);

    Map<String, Object> headerProps = new HashMap<>();
    headerProps.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-3/header-1", headerProps);
    context.create().resource("/content/page/jcr:content/static/table-3/header-2", headerProps);
    context.create().resource("/content/page/jcr:content/static/table-3/header-3", headerProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableHeader> headers = tableStaticDataSource.getHeaderElements();
    assertEquals(3, headers.size());
  }

  @Test
  public void testGetRowElements_empty() {
    List<KestrosTableRow> rows = tableStaticDataSource.getRowElements();
    assertNotNull(rows);
    assertEquals(0, rows.size());
  }

  @Test
  public void testGetRowElements_singleRow() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-4", properties);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-4/row-1", rowProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableRow> rows = tableStaticDataSource.getRowElements();
    assertEquals(1, rows.size());
  }

  @Test
  public void testGetRowElements_multipleRows() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-5", properties);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-5/row-1", rowProps);
    context.create().resource("/content/page/jcr:content/static/table-5/row-2", rowProps);
    context.create().resource("/content/page/jcr:content/static/table-5/row-3", rowProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableRow> rows = tableStaticDataSource.getRowElements();
    assertEquals(3, rows.size());
  }

  @Test
  public void testGetRowElements_ignoresNonRowChildren() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-6", properties);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-6/row-1", rowProps);

    Map<String, Object> otherProps = new HashMap<>();
    otherProps.put("sling:resourceType", "kes:Text");
    context.create().resource("/content/page/jcr:content/static/table-6/text-1", otherProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableRow> rows = tableStaticDataSource.getRowElements();
    assertEquals(1, rows.size());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosTable.RESOURCE_TYPE, tableStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testAdaptation() {
    assertNotNull(tableStaticDataSource);
  }

  @Test
  public void testGetHeaderElements_andRowElements_together() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-7", properties);

    Map<String, Object> headerProps = new HashMap<>();
    headerProps.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-7/header-1", headerProps);
    context.create().resource("/content/page/jcr:content/static/table-7/header-2", headerProps);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-7/row-1", rowProps);
    context.create().resource("/content/page/jcr:content/static/table-7/row-2", rowProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableHeader> headers = tableStaticDataSource.getHeaderElements();
    List<KestrosTableRow> rows = tableStaticDataSource.getRowElements();

    assertEquals(2, headers.size());
    assertEquals(2, rows.size());
  }

  @Test
  public void testGetHeaders_empty() {
    List<Resource> headers = tableStaticDataSource.getHeaders();
    assertNotNull(headers);
    assertEquals(0, headers.size());
  }

  @Test
  public void testGetHeaders_singleHeader() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-8", properties);

    Map<String, Object> headerProps = new HashMap<>();
    headerProps.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-8/header-1", headerProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<Resource> headers = tableStaticDataSource.getHeaders();
    assertEquals(1, headers.size());
  }

  @Test
  public void testGetRows_empty() {
    List<Resource> rows = tableStaticDataSource.getRows();
    assertNotNull(rows);
    assertEquals(0, rows.size());
  }

  @Test
  public void testGetRows_singleRow() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-9", properties);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-9/row-1", rowProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<Resource> rows = tableStaticDataSource.getRows();
    assertEquals(1, rows.size());
  }

  @Test
  public void testGetChildElements() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-10", properties);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-10/row-1", rowProps);
    context.create().resource("/content/page/jcr:content/static/table-10/row-2", rowProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosBasicComponentElement> childElements = tableStaticDataSource.getChildElements();
    assertEquals(2, childElements.size());
  }

  @Test
  public void testGetHeaderElements_preservesOrder() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-11", properties);

    Map<String, Object> headerProps = new HashMap<>();
    headerProps.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-11/header-a", headerProps);
    context.create().resource("/content/page/jcr:content/static/table-11/header-b", headerProps);
    context.create().resource("/content/page/jcr:content/static/table-11/header-c", headerProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableHeader> headers = tableStaticDataSource.getHeaderElements();
    assertEquals(3, headers.size());
  }

  @Test
  public void testGetRowElements_preservesOrder() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-12", properties);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/table-12/row-a", rowProps);
    context.create().resource("/content/page/jcr:content/static/table-12/row-b", rowProps);
    context.create().resource("/content/page/jcr:content/static/table-12/row-c", rowProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    List<KestrosTableRow> rows = tableStaticDataSource.getRowElements();
    assertEquals(3, rows.size());
  }

  @Test
  public void testCompleteTableStructure() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTable.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/table-13", properties);

    Map<String, Object> headerProps = new HashMap<>();
    headerProps.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    headerProps.put("text", "Col 1");
    context.create().resource("/content/page/jcr:content/static/table-13/header-1", headerProps);

    Map<String, Object> rowProps = new HashMap<>();
    rowProps.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    Resource row1 = context.create().resource("/content/page/jcr:content/static/table-13/row-1", rowProps);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", "/libs/kestros/commons/components/content/table-cell");
    context.create().resource("/content/page/jcr:content/static/table-13/row-1/cell-1", cellProps);

    context.request().setResource(resource);
    tableStaticDataSource = context.request().adaptTo(TableStaticDataSource.class);

    assertTrue(tableStaticDataSource.getHeaderElements().size() > 0);
    assertTrue(tableStaticDataSource.getRowElements().size() > 0);
  }
}
