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

package io.kestros.cms.components.basic.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.models.Asset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 * Mirrors ContentPageSorterTest for the asset-backed card list.
 */
public class AssetSorterTest {

  private Asset asset(final String name, final String title, final boolean dated) {
    final Asset asset = mock(Asset.class);
    when(asset.getName()).thenReturn(name);
    when(asset.getTitle()).thenReturn(title);
    if (dated) {
      when(asset.getCreatedDate()).thenReturn(Calendar.getInstance().getTime());
      when(asset.getModifiedDate()).thenReturn(Calendar.getInstance().getTime());
    }
    return asset;
  }

  private List<Asset> assets() {
    return new ArrayList<>(Arrays.asList(
        asset("zulu", "Apple", true),
        asset("alpha", "Zebra", false),
        asset("mike", null, false)));
  }

  @Test
  public void testSortByName() {
    final List<Asset> assets = assets();
    AssetSorter.sort(assets, "name");

    assertEquals("alpha", assets.get(0).getName());
    assertEquals("mike", assets.get(1).getName());
    assertEquals("zulu", assets.get(2).getName());
  }

  /** An asset with no title sorts by its node name instead. */
  @Test
  public void testSortByAnythingElseUsesTheTitle() {
    final List<Asset> assets = assets();
    AssetSorter.sort(assets, "title");

    // "Apple", "Zebra", then "mike" - the titleless asset falls back to its node name, and
    // lower-case sorts after upper-case.
    assertEquals("zulu", assets.get(0).getName());
    assertEquals("alpha", assets.get(1).getName());
    assertEquals("mike", assets.get(2).getName());
  }

  /** An asset with no dates scores 0 rather than throwing. */
  @Test
  public void testSortByCreatedDateToleratesAnAssetWithNoDates() {
    final List<Asset> assets = assets();
    AssetSorter.sort(assets, "createdDate");

    assertEquals("zulu", assets.get(2).getName());
  }

  @Test
  public void testSortByLastModifiedToleratesAnAssetWithNoDates() {
    final List<Asset> assets = assets();
    AssetSorter.sort(assets, "lastModified");

    assertEquals("zulu", assets.get(2).getName());
  }

  @Test
  public void testSortByEmptyLeavesTheOrderAlone() {
    final List<Asset> assets = assets();
    AssetSorter.sort(assets, "");

    assertEquals("zulu", assets.get(0).getName());
  }
}
