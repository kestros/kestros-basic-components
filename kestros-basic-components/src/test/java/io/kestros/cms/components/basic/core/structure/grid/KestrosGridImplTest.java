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
package io.kestros.cms.components.basic.core.structure.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import io.kestros.cms.components.basic.core.structure.container.KestrosContainerImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosGridImplTest extends BaseSyntheticTest {

  private KestrosGridImpl grid;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    List<KestrosContainer> columns = new ArrayList<>();
    columns.add(new KestrosContainerImpl(
        new ArrayList<KestrosBasicComponentElement>(), dataSource, "column", "col1"));
    columns.add(new KestrosContainerImpl(
        new ArrayList<KestrosBasicComponentElement>(), dataSource, "column", "col2"));

    grid = new KestrosGridImpl(columns, dataSource, "grid", "gridElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = grid.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/gridElement", syntheticResource.getPath());
  }

  /**
   * The constructor stored its columns in a field nothing read, so the interface default answered
   * getChildElements() with an empty list and a grid rendered with none of the columns it was
   * built from.
   */
  @Test
  public void testGetChildElementsReturnsTheColumns() {
    assertEquals(2, grid.getChildElements().size());
  }

  @Test
  public void testGetChildrenIsBuiltFromTheColumns() {
    assertEquals(2, grid.getChildren().size());
  }

  /** The returned list is a copy, so a caller cannot add a column to a built grid. */
  @Test
  public void testGetChildElementsReturnsACopy() {
    grid.getChildElements().clear();

    assertEquals(2, grid.getChildElements().size());
  }
}
