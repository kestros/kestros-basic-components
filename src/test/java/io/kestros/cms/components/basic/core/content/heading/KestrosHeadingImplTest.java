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
package io.kestros.cms.components.basic.core.content.heading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosHeadingImplTest extends BaseSyntheticTest {

  private KestrosHeadingImpl heading;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    heading = new KestrosHeadingImpl("Hello World", "h2", dataSource, "heading", "headingElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = heading.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/headingElement", syntheticResource.getPath());
    assertEquals("Hello World",
        syntheticResource.getValueMap().get("headingText", String.class));
    assertEquals("h2",
        syntheticResource.getValueMap().get("headingType", String.class));
  }

  @Test
  public void testGetHeadingText() {
    assertEquals("Hello World", heading.getHeadingText());
  }

  @Test
  public void testGetHeadingType() {
    assertEquals("h2", heading.getHeadingType());
  }
}
