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
package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosCardListImplTest extends BaseSyntheticTest {

  private KestrosCardListImpl cardList;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    List<KestrosCard> cards = new ArrayList<>();
    cards.add(new KestrosCardImpl(
        "Card 1 description",
        new KestrosHeadingImpl("Card 1", "h3", dataSource, "title", "title1"),
        new KestrosImageImpl("/content/dam/img1.jpg", "Alt 1", null, null, null, null, null,
            AnchorTarget.SAME_WINDOW, dataSource, "image", "img1", assetRetrievalService),
        new KestrosButtonGroupImpl(new ArrayList<>(), dataSource, "bg", "bg1"),
        dataSource, "card", "card1"
    ));

    cardList = new KestrosCardListImpl(cards, dataSource, "cardlist", "cardListElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = cardList.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/cardListElement", syntheticResource.getPath());
  }

  @Test
  public void testGetCardElements() {
    assertNotNull(cardList.getCardElements());
    assertEquals(1, cardList.getCardElements().size());
  }
}
