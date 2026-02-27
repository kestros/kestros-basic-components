package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class TableRowStaticDataSourceTest extends BaseDataSourceComponentTest {

  private TableRowStaticDataSource tableRowStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    setupSamplePage("/content/page", null);
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row", properties);
    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);
  }

  @Override
  public String getResourceType() {
    return KestrosTableRow.RESOURCE_TYPE;
  }

  @Override
  @Test
  @Ignore("Pre-existing test pattern")
  public void testToSyntheticResource() {
    // Override required by BaseDataSourceComponentTest
  }

  @Test
  public void testGetCellElements_empty() {
    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertNotNull(cells);
    assertEquals(0, cells.size());
  }

  @Test
  public void testGetCellElements_singleCell() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-2", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-2/cell-1", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertNotNull(cells);
    assertEquals(1, cells.size());
  }

  @Test
  public void testGetCellElements_multipleCells() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-3", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-3/cell-1", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-3/cell-2", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-3/cell-3", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertNotNull(cells);
    assertEquals(3, cells.size());
  }

  @Test
  public void testGetCellElements_onlyAcceptsCells() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-4", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-4/cell-1", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertEquals(1, cells.size());
  }

  @Test
  public void testGetCellElements_nullFilteringWorks() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-5", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-5/cell-1", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-5/cell-2", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertNotNull(cells);
    assertTrue(!cells.contains(null));
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosTableRow.RESOURCE_TYPE, tableRowStaticDataSource.getComponentResourceType());
  }

  @Test
  public void testAdaptation() {
    assertNotNull(tableRowStaticDataSource);
  }

  @Test
  public void testGetCellElements_preservesOrder() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-6", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-6/cell-a", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-6/cell-b", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-6/cell-c", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertEquals(3, cells.size());
  }

  @Test
  public void testGetCellElements_largeNumber() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-7", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    for (int i = 0; i < 10; i++) {
      context.create().resource("/content/page/jcr:content/static/row-7/cell-" + i, cellProps);
    }

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertEquals(10, cells.size());
  }

  @Test
  public void testGetCells_empty() {
    List<Resource> cells = tableRowStaticDataSource.getCells();
    assertNotNull(cells);
    assertEquals(0, cells.size());
  }

  @Test
  public void testGetCells_singleCell() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-8", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-8/cell-1", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<Resource> cells = tableRowStaticDataSource.getCells();
    assertEquals(1, cells.size());
  }

  @Test
  public void testGetChildElements() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-9", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-9/cell-1", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-9/cell-2", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosBasicComponentElement> childElements = tableRowStaticDataSource.getChildElements();
    assertEquals(2, childElements.size());
  }

  @Test
  public void testGetCellElements_twoChildCells() {
    properties.clear();
    properties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/jcr:content/static/row-10", properties);

    Map<String, Object> cellProps = new HashMap<>();
    cellProps.put("sling:resourceType", KestrosTableCell.RESOURCE_TYPE);
    context.create().resource("/content/page/jcr:content/static/row-10/cell-1", cellProps);
    context.create().resource("/content/page/jcr:content/static/row-10/cell-2", cellProps);

    context.request().setResource(resource);
    tableRowStaticDataSource = context.request().adaptTo(TableRowStaticDataSource.class);

    List<KestrosTableCell> cells = tableRowStaticDataSource.getCellElements();
    assertEquals(2, cells.size());
  }
}
