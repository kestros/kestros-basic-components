package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class TableChildNodesDataSourceTest extends BaseDataSourceTest {

  private TableChildNodesDataSource dataSource;
  private Resource resource;

  @Override
  public void doComponentSetup() {
    final Map<String, Object> row1 = new HashMap<>();
    row1.put("club", "Harborside");
    row1.put("pos", 1L);
    row1.put("pts", 45L);
    context.create().resource("/content/data/standings/row1", row1);
    final Map<String, Object> row2 = new HashMap<>();
    row2.put("club", "Riverside");
    row2.put("pos", 2L);
    row2.put("pts", 38L);
    context.create().resource("/content/data/standings/row2", row2);

    final Map<String, Object> props = new HashMap<>();
    props.put("dataPath", "/content/data/standings");
    props.put("columns", new String[] {"pos", "club", "pts"});
    props.put("headers", new String[] {"Pos", "Club", "Pts"});
    resource = context.create().resource("/content/page/jcr:content/table", props);
    context.request().setResource(resource);
    dataSource = context.request().adaptTo(TableChildNodesDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    final Resource synthetic =
        dataSource.toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content");
    assertEquals(KestrosTable.RESOURCE_TYPE, synthetic.getResourceType());
  }

  @Test
  public void testGetRowElements() {
    assertEquals(2, dataSource.getRowElements().size());
  }

  @Test
  public void testGetRowElementsCells() {
    assertEquals(3, dataSource.getRowElements().get(0).getCellElements().size());
    assertEquals("1", dataSource.getRowElements().get(0).getCellElements().get(0).getText());
    assertEquals("Harborside",
        dataSource.getRowElements().get(0).getCellElements().get(1).getText());
    assertEquals("45", dataSource.getRowElements().get(0).getCellElements().get(2).getText());
  }

  @Test
  public void testGetHeaderElements() {
    assertEquals(3, dataSource.getHeaderElements().size());
    assertEquals("Pos", dataSource.getHeaderElements().get(0).getText());
    assertEquals("Club", dataSource.getHeaderElements().get(1).getText());
  }

  @Test
  public void testGetRowElementsWhenDataPathHasUnresolvedPlaceholder() {
    // {team} with no matching route parameter resolves to empty -> path misses -> no rows, no crash.
    final Map<String, Object> props = new HashMap<>();
    props.put("dataPath", "/content/data/teams/{team}/results");
    props.put("columns", new String[] {"pos"});
    final Resource dyn = context.create().resource("/content/page/jcr:content/tabledyn", props);
    context.request().setResource(dyn);
    final TableChildNodesDataSource dynDataSource =
        context.request().adaptTo(TableChildNodesDataSource.class);
    assertEquals(0, dynDataSource.getRowElements().size());
  }

  @Test
  public void testGetRowElementsWhenNoDataPath() {
    final Resource bare = context.create().resource("/content/page/jcr:content/table2",
        new HashMap<>());
    context.request().setResource(bare);
    final TableChildNodesDataSource emptyDataSource =
        context.request().adaptTo(TableChildNodesDataSource.class);
    assertEquals(0, emptyDataSource.getRowElements().size());
  }

  /** With no headers property configured, the header list is empty rather than null. */
  @Test
  public void testGetHeaderElementsWhenNoHeadersAreConfigured() {
    final Map<String, Object> props = new HashMap<>();
    props.put("dataPath", "/content/data/standings");
    props.put("columns", new String[] {"pos", "club"});
    final Resource componentResource =
        context.create().resource("/content/page/jcr:content/table-no-headers", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(TableChildNodesDataSource.class)
        .getHeaderElements().isEmpty());
  }

  /** dataPath and columns are both required; missing either yields no rows. */
  @Test
  public void testGetRowElementsWhenNoDataPathIsConfigured() {
    final Map<String, Object> props = new HashMap<>();
    props.put("columns", new String[] {"pos", "club"});
    final Resource componentResource =
        context.create().resource("/content/page/jcr:content/table-no-path", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(TableChildNodesDataSource.class)
        .getRowElements().isEmpty());
  }

  @Test
  public void testGetRowElementsWhenNoColumnsAreConfigured() {
    final Map<String, Object> props = new HashMap<>();
    props.put("dataPath", "/content/data/standings");
    final Resource componentResource =
        context.create().resource("/content/page/jcr:content/table-no-columns", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(TableChildNodesDataSource.class)
        .getRowElements().isEmpty());
  }

  /** A dataPath with no placeholder skips the resolution step entirely. */
  @Test
  public void testGetRowElementsWithAPlainDataPath() {
    final Map<String, Object> props = new HashMap<>();
    props.put("dataPath", "/content/data/standings");
    props.put("columns", new String[] {"pos"});
    final Resource componentResource =
        context.create().resource("/content/page/jcr:content/table-plain-path", props);
    context.request().setResource(componentResource);

    assertEquals(2, context.request().adaptTo(TableChildNodesDataSource.class)
        .getRowElements().size());
  }
}
