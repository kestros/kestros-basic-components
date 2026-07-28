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

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardListAssetsDataSourceTest extends BaseDataSourceTest {

  private CardListAssetsDataSource cardListAssetsDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/page", null);
    setUpSampleCollection("/content/assets/collection");

    properties.put("collectionPath", "/content/assets/collection");
    resource = context.create().resource("/content/page/cardlist/assets", properties);
    context.request().setResource(resource);
  }

  @Override
  public void doComponentTypeSetup() {

  }

  @Override
  public void testToSyntheticResource() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertNotNull(
            cardListAssetsDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetCollection() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertNotNull(cardListAssetsDataSource.getCollection());
  }

  @Test
  public void testGetCards() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_defaultSortBy_returnsNaturalOrder() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByEmpty_returnsAll() {
    registerAssetRetrievalService();
    properties.put("sortBy", "");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-empty", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByTitle() {
    registerAssetRetrievalService();
    properties.put("sortBy", "title");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-title", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByName() {
    registerAssetRetrievalService();
    properties.put("sortBy", "name");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-name", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_reverseOrder() {
    registerAssetRetrievalService();
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/cardlist/assets-reverse", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_reverseOrderDefault_false() {
    registerAssetRetrievalService();
    resource = context.create().resource("/content/page/cardlist/assets-no-reverse", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitApplied() {
    registerAssetRetrievalService();
    properties.put("limit", "2");
    resource = context.create().resource("/content/page/cardlist/assets-limit-2", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(2, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitZero_returnsAll() {
    registerAssetRetrievalService();
    properties.put("limit", "0");
    resource = context.create().resource("/content/page/cardlist/assets-limit-0", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitDefault_returnsAll() {
    registerAssetRetrievalService();
    resource = context.create().resource("/content/page/cardlist/assets-no-limit", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitInvalidString_returnsAll() {
    registerAssetRetrievalService();
    properties.put("limit", "not-a-number");
    resource = context.create().resource("/content/page/cardlist/assets-limit-invalid", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByNameAndReverse() {
    registerAssetRetrievalService();
    properties.put("sortBy", "name");
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/cardlist/assets-name-reverse", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByTitleAndLimit() {
    registerAssetRetrievalService();
    properties.put("sortBy", "title");
    properties.put("limit", "1");
    resource = context.create().resource("/content/page/cardlist/assets-title-limit", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(1, cardListAssetsDataSource.getCardElements().size());
  }

  /**
   * The createdDate and lastModified comparators had no coverage: the existing sort cases only
   * exercise name and title, which share a different lambda.
   */
  @Test
  public void testGetCardElements_sortByCreatedDate() {
    registerAssetRetrievalService();
    properties.put("sortBy", "createdDate");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-created",
        properties);
    context.request().setResource(resource);

    assertEquals(3, context.request().adaptTo(CardListAssetsDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByLastModified() {
    registerAssetRetrievalService();
    properties.put("sortBy", "lastModified");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-modified",
        properties);
    context.request().setResource(resource);

    assertEquals(3, context.request().adaptTo(CardListAssetsDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetHeadingLevelDefaultsToH2() {
    registerAssetRetrievalService();
    resource = context.create().resource("/content/page/cardlist/assets-default-heading",
        properties);
    context.request().setResource(resource);

    assertEquals("h2",
        context.request().adaptTo(CardListAssetsDataSource.class).getHeadingLevel());
  }

  @Test
  public void testGetHeadingLevelWhenConfigured() {
    registerAssetRetrievalService();
    properties.put("headingType", "h3");
    resource = context.create().resource("/content/page/cardlist/assets-h3", properties);
    context.request().setResource(resource);

    assertEquals("h3",
        context.request().adaptTo(CardListAssetsDataSource.class).getHeadingLevel());
  }

  /**
   * getCardElements is annotated @Nonnull but returned null when a single asset's image could not
   * be built, so one bad asset discarded the whole list and handed HTL a null. It skips that card
   * now, which is what the sibling card lists already do.
   */
  @Test
  public void testGetCardElementsSkipsAnAssetWhoseImageCannotBeBuilt() {
    registerAssetRetrievalService();
    // An asset with no path cannot produce an image; the other three still render.
    context.create().resource("/content/assets/collection/asset-4", new HashMap<>());
    resource = context.create().resource("/content/page/cardlist/assets-partial", properties);
    context.request().setResource(resource);

    assertNotNull(context.request().adaptTo(CardListAssetsDataSource.class).getCardElements());
  }

  @Test
  public void testGetCardElementsIsNeverNull() {
    registerAssetRetrievalService();
    final Map<String, Object> props = new HashMap<>();
    props.put("collectionPath", "/content/assets/nowhere");
    resource = context.create().resource("/content/page/cardlist/assets-missing", props);
    context.request().setResource(resource);

    assertNotNull(context.request().adaptTo(CardListAssetsDataSource.class).getCardElements());
  }
}
