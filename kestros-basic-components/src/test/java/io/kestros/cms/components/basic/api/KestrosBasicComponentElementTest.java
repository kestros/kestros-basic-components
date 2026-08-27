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

package io.kestros.cms.components.basic.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.alert.KestrosAlertImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class KestrosBasicComponentElementTest extends BaseSyntheticTest {
  private KestrosAlertImpl alert;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    List<ComponentVariation> variationList = new ArrayList<>();
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    alert = new KestrosAlertImpl("Heading", "Text", dataSource, "alert", "forcedResourceName");
  }

  @Test
  public void testGetLayout() {
    assertEquals("default", alert.getLayout());
  }

  @Test
  public void testGetAppliedVariations() {

  }

  @Ignore
  @Test
  public void testGetId() {
    assertEquals("id", alert.getId());
  }

  @Test
  public void testGetUiFramework() {
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetResource() {
    assertNotNull(alert.getResource());
    assertEquals("/synthetics/content/parent/forcedResourceName", alert.getResource().getPath());
  }

  @Test
  public void testGetResourceResolver() {
    assertNotNull(alert.getResourceResolver());
  }

  @Test
  public void testGetParentPath() {
    assertEquals("/content/parent", alert.getParentPath());
  }

  @Test
  public void testGetVariations() {
    assertEquals(0, alert.getVariations().size());
  }

  @Test
  public void testGetInlineVariations() {
    assertEquals("", alert.getInlineVariations());
  }

  @Test
  public void testGetPath() {
    assertEquals("/synthetics/content/parent/forcedResourceName", alert.getPath());
  }

  @Test
  public void testIsSynthetic() {
    assertTrue(alert.isSynthetic());
  }

  @Test
  public void testIsDataSourceComponent() {
    assertTrue(alert.isDataSourceComponent());
  }

  @Test
  public void testGetRequestAttributes() {

  }

  @Test
  public void testToSyntheticResource() {
  }

  @Test
  public void testTestGetLayout() {
  }

  @Test
  public void testGetComponentResourceType() {
  }

  @Test
  public void testGetForcedResourceName() {
  }

  @Test
  public void testGetComponentVariationRetrievalService() {
  }

  @Test
  public void testGetComponentUiFrameworkViewRetrievalService() {
  }


}
