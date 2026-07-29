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
package io.kestros.cms.components.basic.core.lists.linklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosLinkListImplTest extends BaseSyntheticTest {

  private KestrosLinkListImpl linkList;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    List<KestrosLink> links = new ArrayList<>();
    links.add(new KestrosLinkImpl("Link 1", "/content/page1.html", null, null, null, null, null,
        null, dataSource, "link", "link1"));
    links.add(new KestrosLinkImpl("Link 2", "/content/page2.html", null, null, null, null, null,
        null, dataSource, "link", "link2"));

    linkList = new KestrosLinkListImpl(links, dataSource, "linkList", "linkListElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = linkList.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/linkListElement", syntheticResource.getPath());
  }

  @Test
  public void testGetLinkElements() {
    assertNotNull(linkList.getLinkElements());
    assertEquals(2, linkList.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsIsDefensiveCopy() {
    List<KestrosLink> links = linkList.getLinkElements();
    links.clear();
    assertEquals(2, linkList.getLinkElements().size());
  }
}
