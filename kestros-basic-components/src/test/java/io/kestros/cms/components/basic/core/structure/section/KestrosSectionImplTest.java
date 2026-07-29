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
package io.kestros.cms.components.basic.core.structure.section;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.richtext.KestrosRichTextImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosSectionImplTest extends BaseSyntheticTest {

  private KestrosSectionImpl section;
  private KestrosSectionImpl sectionWithBackground;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    List<KestrosBasicComponentElement> elements = new ArrayList<>();
    elements.add(new KestrosRichTextImpl("<p>Child</p>", dataSource, "richtext", "child1"));

    section = new KestrosSectionImpl(null, elements, dataSource, "section", "sectionElement");
    sectionWithBackground = new KestrosSectionImpl("/content/assets/bg.jpg", elements, dataSource,
        "section", "sectionWithBg");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = section.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/sectionElement", syntheticResource.getPath());
  }

  @Test
  public void testGetBackgroundImageWhenNull() {
    assertNull(section.getBackgroundImage());
  }

  @Test
  public void testGetBackgroundImage() {
    assertEquals("/content/assets/bg.jpg", sectionWithBackground.getBackgroundImage());
  }

  @Test
  public void testGetChildElements() {
    assertNotNull(section.getChildElements());
    assertEquals(1, section.getChildElements().size());
  }
}
