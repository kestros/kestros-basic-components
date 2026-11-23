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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.Collections;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosTableCellImplTest extends BaseSyntheticTest {

  private KestrosTableCellImpl cell;
  private CardListStaticDataSource dataSource;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    dataSource = context.request().adaptTo(CardListStaticDataSource.class);
    cell = new KestrosTableCellImpl("45", dataSource, "cell", "cellElement");
  }

  @Override
  public void testToSyntheticResource() throws ComponentConfigurationException {
    Resource syntheticResource = cell.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/cellElement", syntheticResource.getPath());
  }

  @Test
  public void testGetText() {
    assertEquals("45", cell.getText());
  }

  @Test
  public void testSyntheticResourceCarriesText() {
    // Regression: getCellContent() returns List<Resource> and must be @JsonIgnore, else it corrupts
    // the ObjectMapper serialization of the synthetic resource and the cell's text is dropped.
    final Resource synthetic = cell.toSyntheticResource(getResourceResolver(), "/parent");
    assertEquals("45", synthetic.getValueMap().get("text", String.class));
  }

  @Test
  public void testGetCellContentElementsWhenTextOnly() {
    assertTrue(cell.getCellContentElements().isEmpty());
  }

  @Test
  public void testContentElementCell() throws ComponentConfigurationException {
    KestrosHeadingImpl heading =
        new KestrosHeadingImpl("Harborside", "h3", dataSource, "content", "contentElement");
    List<KestrosBasicComponentElement> content = Collections.singletonList(heading);
    KestrosTableCellImpl richCell =
        new KestrosTableCellImpl(content, dataSource, "cell2", "cell2Element");
    assertEquals(1, richCell.getCellContentElements().size());
    assertNull(richCell.getText());
  }
}
