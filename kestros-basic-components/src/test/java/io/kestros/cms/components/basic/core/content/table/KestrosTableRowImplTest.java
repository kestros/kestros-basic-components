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

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.Arrays;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosTableRowImplTest extends BaseSyntheticTest {

  private KestrosTableRowImpl row;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);
    KestrosTableCell c1 = new KestrosTableCellImpl("1", dataSource, "cell0", "cell0Element");
    KestrosTableCell c2 = new KestrosTableCellImpl("Harborside", dataSource, "cell1", "cell1Element");
    List<KestrosTableCell> cells = Arrays.asList(c1, c2);
    row = new KestrosTableRowImpl(cells, dataSource, "row", "rowElement");
  }

  @Override
  public void testToSyntheticResource() throws ComponentConfigurationException {
    Resource syntheticResource = row.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/rowElement", syntheticResource.getPath());
  }

  @Test
  public void testGetCellElements() {
    assertEquals(2, row.getCellElements().size());
  }

  @Test
  public void testGetCellElementsText() {
    assertEquals("1", row.getCellElements().get(0).getText());
    assertEquals("Harborside", row.getCellElements().get(1).getText());
  }
}
