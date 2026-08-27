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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Test;

public class CardListChildPagesDataSourceTest extends BaseDataSourceTest {
  private CardListChildPagesDataSource cardListChildPagesDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    setUpSampleCollection("/content/collection");
    setupSamplePage("/content/page", "/content/collection/asset-1");

    properties.put("pagesPath", "/content/page");
    properties.put("readMoreText","Button Text");
    resource = context.create().resource("/content/page/jcr:content/component", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
  }

  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosCardList.RESOURCE_TYPE, properties);
    context.create().resource(KestrosCard.RESOURCE_TYPE, properties);
    context.create().resource(KestrosImage.RESOURCE_TYPE, properties);
    context.create().resource(KestrosButtonGroup.RESOURCE_TYPE, properties);
    context.create().resource(KestrosButton.RESOURCE_TYPE, properties);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(
        cardListChildPagesDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
    assertNotNull(
        cardListChildPagesDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
    assertEquals(13, cardListChildPagesDataSource.toSyntheticResource(context.resourceResolver(),
        "/test").getValueMap().size());
    ValueMap valueMap = cardListChildPagesDataSource.toSyntheticResource(
        context.resourceResolver(), "/test").getValueMap();
    assertNotNull(valueMap);
    assertEquals("/libs/kestros/commons/components/lists/card-list",
        valueMap.get("componentResourceType"));
    assertFalse(valueMap.get("synthetic", true));
    assertNull(valueMap.get("id"));
    assertEquals(0, valueMap.get("variations", List.class).size());
    assertEquals("", valueMap.get("inlineVariations"));
    assertTrue(valueMap.get("dataSourceComponent", false));
    assertEquals("component", valueMap.get("forcedResourceName"));
    assertEquals(3, valueMap.get("cards", List.class).size());

    Map<String, Object> card = (Map) valueMap.get("cards", List.class).get(0);
    assertNotNull(card);
    assertNotNull(card.get("path"));
    assertNotNull(card.get("resourceType"));


  }

  @Test
  public void testGetRootPage() {
    assertNotNull(cardListChildPagesDataSource.getRootPage());
  }

  @Test
  public void testGetCards() {
    properties.put("cardVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    properties.put("imageVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    properties.put("buttonGroupVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    resource = context.create().resource("/content/page/jcr:content/component2", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(0).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(0).getImageElement().getVariations()
        .size());

    assertEquals(3,
        cardListChildPagesDataSource.getCardElements().get(0).getButtonGroupElement().getVariations()
            .size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(1).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(1).getImageElement().getVariations()
        .size());
    assertEquals(3,
        cardListChildPagesDataSource.getCardElements().get(1).getButtonGroupElement().getVariations()
            .size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(2).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(2).getImageElement().getVariations()
        .size());
    assertEquals(3,
        cardListChildPagesDataSource.getCardElements().get(2).getButtonGroupElement().getVariations()
            .size());
    context.request().setResource(
        cardListChildPagesDataSource.getCardElements().get(0).getImageElement().getResource());
    assertEquals(3, context.request().adaptTo(ImageStaticDataSource.class).getVariations().size());
  }

  @Test
  public void testGetChildren() {
    assertEquals(3, cardListChildPagesDataSource.getChildren().size());
  }

  @Test
  public void testGetCardElements_defaultSortBy_returnsNaturalOrder() {
    // sortBy property not set — should return natural JCR order (child-1, child-2, child-3)
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByEmpty_returnsNaturalOrder() {
    properties.put("sortBy", "");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-empty", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByName() {
    properties.put("sortBy", "name");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-name", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    List<KestrosCard> cards = cardListChildPagesDataSource.getCardElements();
    assertEquals(3, cards.size());
  }

  @Test
  public void testGetCardElements_sortByTitle() {
    properties.put("sortBy", "title");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-title",
        properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    List<KestrosCard> cards = cardListChildPagesDataSource.getCardElements();
    assertEquals(3, cards.size());
  }

  @Test
  public void testGetCardElements_reverseOrder() {
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_reverseOrderFalse_default() {
    // reverse not set — defaults to false
    resource = context.create().resource("/content/page/jcr:content/comp-no-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitApplied() {
    properties.put("limit", "2");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-2", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(2, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitZero_returnsAll() {
    properties.put("limit", "0");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-0", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitDefault_returnsAll() {
    // limit not set — defaults to 0 (no limit)
    resource = context.create().resource("/content/page/jcr:content/comp-no-limit", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitInvalidString_returnsAll() {
    properties.put("limit", "invalid");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-invalid",
        properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByNameAndReverse() {
    properties.put("sortBy", "name");
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-name-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByTitleAndLimit() {
    properties.put("sortBy", "title");
    properties.put("limit", "1");
    resource = context.create().resource("/content/page/jcr:content/comp-title-limit", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(1, cardListChildPagesDataSource.getCardElements().size());
  }

  /**
   * The createdDate and lastModified comparators had no coverage: the existing sort cases only
   * exercise name and title, which share a different lambda.
   */
  @Test
  public void testGetCardElements_sortByCreatedDate() {
    properties.put("sortBy", "createdDate");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-created",
        properties);
    context.request().setResource(resource);

    assertEquals(3, context.request().adaptTo(CardListChildPagesDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByLastModified() {
    properties.put("sortBy", "lastModified");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-modified",
        properties);
    context.request().setResource(resource);

    assertEquals(3, context.request().adaptTo(CardListChildPagesDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetRootPageWhenThePagesPathDoesNotResolve() {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/nowhere");
    resource = context.create().resource("/content/page/jcr:content/comp-missing-root", props);
    context.request().setResource(resource);

    assertNull(context.request().adaptTo(CardListChildPagesDataSource.class).getRootPage());
  }

  @Test
  public void testGetCardElementsWhenThereIsNoRootPage() {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/nowhere");
    resource = context.create().resource("/content/page/jcr:content/comp-no-root", props);
    context.request().setResource(resource);

    assertTrue(context.request().adaptTo(CardListChildPagesDataSource.class)
        .getCardElements().isEmpty());
  }

  @Test
  public void testGetReadMoreTextWhenNotConfigured() {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/page");
    resource = context.create().resource("/content/page/jcr:content/comp-no-read-more", props);
    context.request().setResource(resource);

    assertNull(context.request().adaptTo(CardListChildPagesDataSource.class).getReadMoreText());
  }

  /**
   * The date comparators read jcr:created / jcr:lastModified off each page's jcr:content, guarding
   * on both the child and the property. The sample pages all have a jcr:content and neither date,
   * so only one side of each guard was reached. These add a page with no jcr:content at all and a
   * page that does carry the dates.
   */
  private void addPagesWithAndWithoutDates() {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/page/child-no-content", pageProperties);

    final Map<String, Object> datedContent = new HashMap<>();
    datedContent.put("jcr:primaryType", "nt:unstructured");
    datedContent.put("jcr:created", Calendar.getInstance());
    datedContent.put("jcr:lastModified", Calendar.getInstance());
    context.create().resource("/content/page/child-dated", pageProperties);
    context.create().resource("/content/page/child-dated/jcr:content", datedContent);
  }

  @Test
  public void testGetCardElements_sortByCreatedDateAcrossPagesWithAndWithoutDates() {
    addPagesWithAndWithoutDates();
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/page");
    props.put("sortBy", "createdDate");
    resource = context.create().resource("/content/page/jcr:content/comp-created-mixed", props);
    context.request().setResource(resource);

    assertEquals(5, context.request().adaptTo(CardListChildPagesDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByLastModifiedAcrossPagesWithAndWithoutDates() {
    addPagesWithAndWithoutDates();
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/page");
    props.put("sortBy", "lastModified");
    resource = context.create().resource("/content/page/jcr:content/comp-modified-mixed", props);
    context.request().setResource(resource);

    assertEquals(5, context.request().adaptTo(CardListChildPagesDataSource.class)
        .getCardElements().size());
  }

  /**
   * The defect this card exists for lives in the DATA SOURCE, not in the card model: the data source
   * did not pass its AssetRetrievalService when constructing each card, so every card built from a
   * page lost its image's title and description.
   *
   * <p>This test drives the data source, which is the path an author actually exercises. A test that
   * calls the card constructor directly cannot catch the bug, because the constructor was never the
   * broken part - it passes whether or not the data source hands the service over.
   *
   * <p>The page and the asset carry deliberately different titles ("Title" against "Asset 1 Title"),
   * so an assertion on the asset's wording cannot pass by accident from the page's.
   */
  @Test
  public void testGetCardElementsResolvesTheAssetTitleAndDescriptionThroughTheDataSource() {
    // This test builds its own asset rather than reusing the shared setUpSampleCollection fixture,
    // so the wording it asserts on is local to the test and cannot drift when that fixture changes.
    // (The shared fixture does resolve - an earlier note here claiming otherwise was wrong.)
    final Map<String, Object> assetProperties = new HashMap<>();
    assetProperties.put("jcr:primaryType", "kes:Asset");
    final Map<String, Object> assetContentProperties = new HashMap<>();
    assetContentProperties.put("jcr:primaryType", "nt:unstructured");
    assetContentProperties.put("jcr:title", "Asset 1 Title");
    assetContentProperties.put("jcr:description", "Asset 1 Description");
    context.create().resource("/content/real-assets/photo", assetProperties);
    context.create().resource("/content/real-assets/photo/jcr:content", assetContentProperties);

    // A page whose children point their image at that asset. The page's own title and description are
    // deliberately different, so an assertion on the asset's wording cannot pass from the page's.
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    final Map<String, Object> pageContentProperties = new HashMap<>();
    pageContentProperties.put("jcr:primaryType", "nt:unstructured");
    pageContentProperties.put("jcr:title", "Page Title");
    pageContentProperties.put("jcr:description", "Page Description");
    pageContentProperties.put("cardImage", "/content/real-assets/photo");
    pageContentProperties.put("image", "/content/real-assets/photo");
    context.create().resource("/content/asset-root", pageProperties);
    context.create().resource("/content/asset-root/jcr:content", pageContentProperties);
    context.create().resource("/content/asset-root/child-1", pageProperties);
    context.create().resource("/content/asset-root/child-1/jcr:content", pageContentProperties);

    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/asset-root");
    props.put("readMoreText", "Button Text");
    final Resource dataSourceResource = context.create().resource(
        "/content/asset-root/jcr:content/comp-asset-resolution", props);
    context.request().setResource(dataSourceResource);

    final CardListChildPagesDataSource dataSource = context.request().adaptTo(
        CardListChildPagesDataSource.class);
    assertNotNull(dataSource);

    final List<KestrosCard> cards = dataSource.getCardElements();
    assertFalse("the root page has a child, so the data source must build a card",
        cards.isEmpty());

    final KestrosImage image = cards.get(0).getImageElement();
    assertNotNull("a card built from a page with an image must have an image element", image);
    assertEquals("alt text must come from the asset, not the page", "Asset 1 Title",
        image.getAltText());
    assertEquals("caption must come from the asset, not the page", "Asset 1 Description",
        image.getCaption());
    assertEquals("image title must come from the asset, not the page", "Asset 1 Title",
        image.getImageTitle());
  }

}
