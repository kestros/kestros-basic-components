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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import io.kestros.cms.components.basic.core.content.button.ButtonDataSourceComponent;
import io.kestros.cms.components.basic.core.content.buttongroup.ButtonGroupDataSourceComponent;
import io.kestros.cms.components.basic.core.content.card.CardDataSourceComponent;
import io.kestros.cms.components.basic.core.content.heading.HeadingDataSourceComponent;
import io.kestros.cms.components.basic.core.content.image.ImageDataSourceComponent;
import io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Test;

public class CardListDataSourceComponentTest extends BaseDataSourceComponentTest {

  private CardListDataSourceComponent cardListStatic;
  private CardListDataSourceComponent cardListChildPages;
  private Resource staticResource;
  private Resource childPagesResource;
  private Map<String, Object> staticProperties = new HashMap<>();
  private Map<String, Object> childPagesProperties = new HashMap<>();


  @Override
  public String getResourceType() {
    return "/libs/kestros/commons/components/lists/card-list";
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/collection");
    setupSamplePage("/content/page", "/content/collection/asset-1");

    childPagesProperties.put("pagesPath", "/content/page");
    childPagesProperties.put("readMoreText", "/content/page");
    childPagesProperties.put("sling:resourceType",
        "/libs/kestros/commons/components/lists/card-list");
    childPagesProperties.put("kes:datasource", "child-pages");
    childPagesResource = context.create()
        .resource("/content/page/jcr:content/child-pages-component", childPagesProperties);
    context.request().setResource(childPagesResource);
    cardListChildPages = context.request().adaptTo(CardListDataSourceComponent.class);

  }

  @Override
  public void testToSyntheticResource() {
    childPagesProperties.put("cardVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("titleVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("imageVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("buttonGroupVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("buttonVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});

    childPagesResource = context.create().resource("/content/page/jcr:content/component2",
        childPagesProperties);
    context.request().setResource(childPagesResource);
    cardListChildPages = context.request().adaptTo(CardListDataSourceComponent.class);
    assertNotNull(
        cardListChildPages.toSyntheticResource(context.resourceResolver(), "/test"));
    assertNotNull(
        cardListChildPages.toSyntheticResource(context.resourceResolver(), "/test"));

    assertEquals(12, cardListChildPages.toSyntheticResource(context.resourceResolver(),
        "/test").getValueMap().size());
    ValueMap valueMap = cardListChildPages.toSyntheticResource(
        context.resourceResolver(), "/test").getValueMap();
    assertNotNull(valueMap);
    assertEquals("/libs/kestros/commons/components/lists/card-list",
        valueMap.get("componentResourceType"));
    assertTrue(valueMap.get("synthetic", false));
    assertNull(valueMap.get("id"));
    assertEquals(0, valueMap.get("variations", List.class).size());
    assertEquals("", valueMap.get("inlineVariations"));
    assertTrue(valueMap.get("dataSourceComponent", false));
    assertEquals("component2", valueMap.get("forcedResourceName"));
    assertEquals(3, valueMap.get("cards", List.class).size());

    Map<String, Object> card = (Map) valueMap.get("cards", List.class).get(0);
    assertNotNull(card);
    assertNotNull(card.get("path"));
    assertNotNull(card.get("resourceType"));

    Resource cardSynthetic = cardListChildPages.getChildren().get(0);
    context.request().setResource(cardSynthetic);
    CardDataSourceComponent cardComponent = context.request().adaptTo(
        CardDataSourceComponent.class);
    Resource titleResource = cardComponent.getTitle();
    Resource imageResource = cardComponent.getImage();
    Resource buttonGroupResource = cardComponent.getButtonGroup();

    assertNotNull(cardComponent);
    assertEquals("/synthetics/content/page/jcr:content/component2/child-1",
        cardComponent.getResource().getPath());
    assertEquals(3, cardComponent.getVariations().size());

    assertEquals("/synthetics/content/page/jcr:content/component2/child-1/imageElement",
        cardComponent.getImage().getPath());

    // title
    context.request().setResource(titleResource);
    HeadingDataSourceComponent headingComponent = context.request().adaptTo(
        HeadingDataSourceComponent.class);
    assertNotNull(headingComponent);
    assertEquals("/synthetics/content/page/jcr:content/component2/child-1/titleElement",
        headingComponent.getResource().getPath());
    assertEquals(3, headingComponent.getVariations().size());

    // image
    context.request().setResource(imageResource);
    ImageDataSourceComponent imageComponent = context.request().adaptTo(
        ImageDataSourceComponent.class);
    assertNotNull(imageComponent);
    assertEquals("/synthetics/content/page/jcr:content/component2/child-1/imageElement",
        imageComponent.getResource().getPath());
    assertEquals(3, imageComponent.getVariations().size());

    // button group
    context.request().setResource(buttonGroupResource);
    ButtonGroupDataSourceComponent buttonGroupComponent = context.request().adaptTo(
        ButtonGroupDataSourceComponent.class);
    assertNotNull(buttonGroupComponent);
    assertEquals("/synthetics/content/page/jcr:content/component2/child-1/buttonGroupElement",
        buttonGroupComponent.getPath());
    assertEquals(3, buttonGroupComponent.getVariations().size());
    assertEquals(1, buttonGroupComponent.getButtonsElements().size());

    // button
    Resource buttonResource = buttonGroupComponent.getButtonsElements().get(0).getResource();
    context.request().setResource(buttonResource);
    ButtonDataSourceComponent buttonComponent = context.request().adaptTo(
        ButtonDataSourceComponent.class);
    assertNotNull(buttonComponent);
    assertEquals(
        "/synthetics/content/page/jcr:content/component2/child-1",
        buttonComponent.getPath());
    assertEquals(3, buttonComponent.getVariations().size());
  }

  @Test
  public void testGetCardElements() {
    childPagesProperties.put("cardVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("imageVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("buttonGroupVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesProperties.put("buttonVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    childPagesResource = context.create().resource("/content/page/jcr:content/component2",
        childPagesProperties);
    context.request().setResource(childPagesResource);
    cardListChildPages = context.request().adaptTo(CardListDataSourceComponent.class);
    assertEquals(3, cardListChildPages.getCardElements().size());
    assertEquals(3, cardListChildPages.getCardElements().get(0).getVariations().size());
    assertEquals(3, cardListChildPages.getCardElements().get(0).getImageElement().getVariations()
        .size());

    assertEquals(3,
        cardListChildPages.getCardElements().get(0).getButtonGroupElement().getVariations()
            .size());
    assertEquals(3, cardListChildPages.getCardElements().get(1).getVariations().size());
    assertEquals(3, cardListChildPages.getCardElements().get(1).getImageElement().getVariations()
        .size());
    assertEquals(3,
        cardListChildPages.getCardElements().get(1).getButtonGroupElement().getVariations()
            .size());
    assertEquals(3, cardListChildPages.getCardElements().get(2).getVariations().size());
    assertEquals(3, cardListChildPages.getCardElements().get(2).getImageElement().getVariations()
        .size());
    assertEquals(3,
        cardListChildPages.getCardElements().get(2).getButtonGroupElement().getVariations()
            .size());
    context.request().setResource(
        cardListChildPages.getCardElements().get(0).getImageElement().getResource());
    assertEquals(3, context.request().adaptTo(ImageStaticDataSource.class).getVariations().size());
  }

  @Test
  public void testGetChildren() {
    assertEquals(3, cardListChildPages.getChildren().size());
  }

  @Test
  public void testGetElementVariationsWhenVariationsPropertyIsMap() {
    List<Map<String, Object>> variationMapList = new ArrayList<>();
    Map<String,Object> variationMap1 = new HashMap<>();
    variationMap1.put("path", "variation1");
    Map<String,Object> variationMap2 = new HashMap<>();
    variationMap2.put("path", "variation2");
    Map<String,Object> variationMap3 = new HashMap<>();
    variationMap3.put("path", "variation3");
    variationMapList.add(variationMap1);
    variationMapList.add(variationMap2);
    variationMapList.add(variationMap3);
    childPagesProperties.put("buttonVariations",
        variationMapList);
    childPagesResource = context.create().resource("/content/page/jcr:content/component2",
        childPagesProperties);
    context.request().setResource(childPagesResource);
    cardListChildPages = context.request().adaptTo(CardListDataSourceComponent.class);

    assertEquals(3, cardListChildPages.getElementVariations("buttonVariations",
        KestrosButton.RESOURCE_TYPE).size());

  }

}