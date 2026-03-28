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

package io.kestros.cms.components.basic.core.content.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListAssetsDataSource;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosAlertImplTest extends BaseSyntheticTest {
  private KestrosAlertImpl alert;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    alert = new KestrosAlertImpl("Heading", "Text", dataSource, "alert", "forcedResourceName");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = alert.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/forcedResourceName", syntheticResource.getPath());
    assertEquals("Heading", syntheticResource.getValueMap().get("heading", String.class));
    assertEquals("Text", syntheticResource.getValueMap().get("text", String.class));
  }

  @Test
  public void testGetHeading() {
    assertEquals("Heading", alert.getHeading());
  }

  @Test
  public void testGetText() {
    assertEquals("Text", alert.getText());
  }
}