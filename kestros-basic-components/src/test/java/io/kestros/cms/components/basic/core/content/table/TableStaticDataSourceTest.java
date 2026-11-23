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

package io.kestros.cms.components.basic.core.content.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class TableStaticDataSourceTest extends BaseDataSourceTest {

  private TableStaticDataSource dataSource;
  private Resource resource;

  private final Map<String, Object> headerProperties = new HashMap<>();
  private final Map<String, Object> rowProperties = new HashMap<>();
  private final Map<String, Object> otherProperties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    headerProperties.put("sling:resourceType", KestrosTableHeader.RESOURCE_TYPE);
    rowProperties.put("sling:resourceType", KestrosTableRow.RESOURCE_TYPE);
    otherProperties.put("sling:resourceType", "kestros/commons/components/content/text");

    resource = context.create().resource("/content/page/jcr:content/table", new HashMap<>());
    context.request().setResource(resource);
    dataSource = context.request().adaptTo(TableStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    final Resource synthetic =
        dataSource.toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content");
    assertEquals(KestrosTable.RESOURCE_TYPE, synthetic.getResourceType());
  }

  @Test
  public void testGetHeaderElements() {
    context.create().resource("/content/page/jcr:content/table/header-1", headerProperties);
    context.create().resource("/content/page/jcr:content/table/header-2", headerProperties);

    assertEquals(2, dataSource.getHeaderElements().size());
  }

  @Test
  public void testGetHeaderElementsWhenThereAreNone() {
    assertTrue(dataSource.getHeaderElements().isEmpty());
  }

  /** Only children whose resource type is the header type count; everything else is skipped. */
  @Test
  public void testGetHeaderElementsSkipsChildrenOfOtherTypes() {
    context.create().resource("/content/page/jcr:content/table/header-1", headerProperties);
    context.create().resource("/content/page/jcr:content/table/row-1", rowProperties);
    context.create().resource("/content/page/jcr:content/table/text-1", otherProperties);

    assertEquals(1, dataSource.getHeaderElements().size());
  }

  @Test
  public void testGetRowElements() {
    context.create().resource("/content/page/jcr:content/table/row-1", rowProperties);
    context.create().resource("/content/page/jcr:content/table/row-2", rowProperties);
    context.create().resource("/content/page/jcr:content/table/row-3", rowProperties);

    assertEquals(3, dataSource.getRowElements().size());
  }

  @Test
  public void testGetRowElementsWhenThereAreNone() {
    assertTrue(dataSource.getRowElements().isEmpty());
  }

  @Test
  public void testGetRowElementsSkipsChildrenOfOtherTypes() {
    context.create().resource("/content/page/jcr:content/table/row-1", rowProperties);
    context.create().resource("/content/page/jcr:content/table/header-1", headerProperties);
    context.create().resource("/content/page/jcr:content/table/text-1", otherProperties);

    assertEquals(1, dataSource.getRowElements().size());
  }

  // ---------------------------------------------------------------------------------------------
  // The container helpers on BaseContainerSlingModelDataSource: getChildren walks the child
  // elements and converts synthetic ones, getChildrenAsType filters by resource type and
  // getChildrenOfType filters by Java type. Exercised here because TableStaticDataSource is a
  // concrete container.
  // ---------------------------------------------------------------------------------------------

  @Test
  public void testGetChildren() {
    context.create().resource("/content/page/jcr:content/table/header-1", headerProperties);
    context.create().resource("/content/page/jcr:content/table/row-1", rowProperties);

    assertNotNull(dataSource.getChildren());
  }

  @Test
  public void testGetChildrenAsTypeFiltersByResourceType() {
    context.create().resource("/content/page/jcr:content/table/header-1", headerProperties);
    context.create().resource("/content/page/jcr:content/table/row-1", rowProperties);
    context.create().resource("/content/page/jcr:content/table/text-1", otherProperties);

    assertEquals(1, dataSource.getChildrenAsType(KestrosTableRow.RESOURCE_TYPE,
        TableRowStaticDataSource.class).size());
  }

  @Test
  public void testGetChildrenAsTypeWhenNothingMatches() {
    context.create().resource("/content/page/jcr:content/table/text-1", otherProperties);

    assertTrue(dataSource.getChildrenAsType(KestrosTableRow.RESOURCE_TYPE,
        TableRowStaticDataSource.class).isEmpty());
  }

  @Test
  public void testGetChildrenOfTypeFiltersByJavaType() {
    context.create().resource("/content/page/jcr:content/table/header-1", headerProperties);
    context.create().resource("/content/page/jcr:content/table/row-1", rowProperties);

    assertNotNull(dataSource.getChildrenOfType(KestrosTableRow.class));
  }

  @Test
  public void testGetChildrenOfTypeWhenThereAreNoChildren() {
    assertTrue(dataSource.getChildrenOfType(KestrosTableRow.class).isEmpty());
  }
}
