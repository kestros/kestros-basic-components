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
package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosCardImplTest extends BaseSyntheticTest {
  private KestrosCardImpl card;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;
  private KestrosHeading title;


  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
            CardListStaticDataSource.class);

    List<ComponentVariation> variations = new ArrayList<>();
    title = new KestrosHeadingImpl(
            "Title",
            "h2",
            dataSource,
            "title",
            "titleElement"
    );
    image = new KestrosImageImpl(
            "/imagePath.jpg",
            "Title Text",
            "http://example.com/image.jpg",
            null,
            null,
            null, null,
            AnchorTarget.SAME_WINDOW,
            dataSource,
            "image",
            "imageElement",
            assetRetrievalService
    );
    buttonGroup = new KestrosButtonGroupImpl(new ArrayList<>(),
            dataSource,
            "buttonGroup",
            "buttonGroupElement"
    );
    card = new KestrosCardImpl(
            "Description",
            title,
            image,
            buttonGroup,
            dataSource,
            "card",
            "forcedResourceName"
    );
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = card.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/forcedResourceName", syntheticResource.getPath());
    assertEquals("Description", syntheticResource.getValueMap().get("description", String.class));

    context.request().setResource(syntheticResource);
    CardStaticDataSource cardStaticDataSource = context.request().adaptTo(
            CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource);
    assertEquals("Description", cardStaticDataSource.getDescription());
    assertEquals("/imagePath.jpg",
            cardStaticDataSource.getImage().getValueMap().get("imagePath", String.class));
    assertEquals("Title",
            cardStaticDataSource.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Title", card.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Description", card.getDescription());
  }

  @Test
  public void testGetImageElement() {
    assertNotNull(card.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    assertNotNull(card.getButtonGroupElement());
  }

}