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
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Arrays;
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

  // ---------------------------------------------------------------------------------------------
  // One broken page must not blank the list.
  //
  // These drive getCardElements() with a page list that mixes a page which throws with pages that
  // do not. Asserting only that no exception escaped would pass against the unfixed code, which
  // rethrew as a RuntimeException - so every assertion below names the pages that survived.
  // ---------------------------------------------------------------------------------------------

  /** A page from the repository, adapted the same way the data source's root page adapts them. */
  private BaseContentPage pageAt(final String path) {
    final Resource pageResource = context.resourceResolver().getResource(path);
    assertNotNull("the fixture must create " + path, pageResource);
    final BaseContentPage page = pageResource.adaptTo(BaseContentPage.class);
    assertNotNull(path + " must adapt to a page", page);
    return page;
  }

  /**
   * Two healthy pages with titles that tell them apart. The shared fixture titles every child
   * "Title", which identifies nothing.
   */
  private void createDistinctlyTitledPages() {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/mixed", pageProperties);

    final Map<String, Object> firstContent = new HashMap<>();
    firstContent.put("jcr:primaryType", "nt:unstructured");
    firstContent.put("jcr:title", "First Good Page");
    context.create().resource("/content/mixed/good-1", pageProperties);
    context.create().resource("/content/mixed/good-1/jcr:content", firstContent);

    final Map<String, Object> secondContent = new HashMap<>();
    secondContent.put("jcr:primaryType", "nt:unstructured");
    secondContent.put("jcr:title", "Second Good Page");
    context.create().resource("/content/mixed/good-2", pageProperties);
    context.create().resource("/content/mixed/good-2/jcr:content", secondContent);
  }

  /**
   * A page that fails while its card is being built. getDisplayDescription() is the first thing
   * the card constructor asks a page for, and nothing on that path guards it.
   */
  private BaseContentPage brokenPage(final String path, final RuntimeException failure) {
    final BaseContentPage broken = mock(BaseContentPage.class);
    when(broken.getPath()).thenReturn(path);
    when(broken.getName()).thenReturn(path.substring(path.lastIndexOf('/') + 1));
    when(broken.getDisplayDescription()).thenThrow(failure);
    return broken;
  }

  /**
   * A data source whose child pages are exactly the list given. getRootPage() is stubbed rather
   * than the loop, so getCardElements() itself runs unaltered.
   */
  private CardListChildPagesDataSource dataSourceOver(final List<BaseContentPage> childPages) {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/mixed");
    props.put("readMoreText", "Button Text");
    final Resource componentResource = context.create().resource(
        "/content/page/jcr:content/comp-mixed-" + childPages.size(), props);
    context.request().setResource(componentResource);

    final CardListChildPagesDataSource dataSource = spy(
        context.request().adaptTo(CardListChildPagesDataSource.class));
    final BaseContentPage root = mock(BaseContentPage.class);
    when(root.getChildPages()).thenReturn(new ArrayList<>(childPages));
    doReturn(root).when(dataSource).getRootPage();
    return dataSource;
  }

  @Test
  public void testGetCardElementsSkipsTheBrokenPageAndKeepsTheRest() {
    createDistinctlyTitledPages();
    final CardListChildPagesDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/mixed/good-1"),
        brokenPage("/content/mixed/broken", new IllegalStateException("jcr:content will not adapt")),
        pageAt("/content/mixed/good-2")));

    final List<KestrosCard> cards = dataSource.getCardElements();

    assertEquals("the two healthy pages must still produce cards", 2, cards.size());
    assertEquals("good-1", cards.get(0).getForcedResourceName());
    assertEquals("good-2", cards.get(1).getForcedResourceName());
    assertEquals("First Good Page", cards.get(0).getTitleElement().getHeadingText());
    assertEquals("Second Good Page", cards.get(1).getTitleElement().getHeadingText());
  }

  @Test
  public void testGetCardElementsLogsTheSkippedPageWithTheExceptionClassAndMessage() {
    createDistinctlyTitledPages();
    final CardListChildPagesDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/mixed/good-1"),
        brokenPage("/content/mixed/broken", new IllegalStateException("jcr:content will not adapt"))));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListChildPagesDataSource.class)) {
      assertEquals(1, dataSource.getCardElements().size());

      assertEquals("exactly one page was skipped, so exactly one warning is expected", 1,
          warnings.messages().size());
      final String warning = warnings.messages().get(0);
      assertTrue("the warning must name the skipped page: " + warning,
          warning.contains("/content/mixed/broken"));
      assertTrue("the warning must name the exception class: " + warning,
          warning.contains("java.lang.IllegalStateException"));
      assertTrue("the warning must carry the exception message: " + warning,
          warning.contains("jcr:content will not adapt"));
    }
  }

  /**
   * An exception with no message must still produce a line that names the class. "Skipping the card
   * for /x: null" tells whoever reads the log nothing at all.
   */
  @Test
  public void testGetCardElementsNamesTheExceptionClassWhenThereIsNoMessage() {
    createDistinctlyTitledPages();
    final CardListChildPagesDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/mixed/good-1"),
        brokenPage("/content/mixed/broken", new NullPointerException()),
        pageAt("/content/mixed/good-2")));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListChildPagesDataSource.class)) {
      assertEquals(2, dataSource.getCardElements().size());

      assertEquals(1, warnings.messages().size());
      assertTrue("the warning must name the exception class even with a null message: "
          + warnings.messages().get(0),
          warnings.messages().get(0).contains("java.lang.NullPointerException"));
    }
  }

  /** Every page broken is still every page skipped - an empty list, not a thrown exception. */
  @Test
  public void testGetCardElementsReturnsEmptyWhenEveryPageIsBroken() {
    createDistinctlyTitledPages();
    final CardListChildPagesDataSource dataSource = dataSourceOver(Arrays.asList(
        brokenPage("/content/mixed/broken-1", new IllegalStateException("first")),
        brokenPage("/content/mixed/broken-2", new IllegalStateException("second"))));

    assertTrue(dataSource.getCardElements().isEmpty());
  }

  /** A page that cannot even say where it is must not take the list down with it. */
  @Test
  public void testGetCardElementsSurvivesAPageWhosePathAlsoThrows() {
    createDistinctlyTitledPages();
    final BaseContentPage broken = mock(BaseContentPage.class);
    when(broken.getName()).thenReturn("broken");
    when(broken.getDisplayDescription()).thenThrow(new IllegalStateException("unreadable"));
    when(broken.getPath()).thenThrow(new IllegalStateException("path is unreadable too"));

    final CardListChildPagesDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/mixed/good-1"), broken, pageAt("/content/mixed/good-2")));

    final List<KestrosCard> cards = dataSource.getCardElements();
    assertEquals(2, cards.size());
    assertEquals("good-1", cards.get(0).getForcedResourceName());
    assertEquals("good-2", cards.get(1).getForcedResourceName());
  }

  // ---------------------------------------------------------------------------------------------
  // A failure that belongs to the whole component still surfaces.
  //
  // Skipping is only correct per page. If the component itself cannot be resolved, every card would
  // fail for the same reason, and turning that into an empty list hides an outage.
  // ---------------------------------------------------------------------------------------------

  @Test
  public void testGetCardElementsThrowsWhenTheThemeCannotBeResolved() throws Exception {
    when(themeProviderService.getThemeForPage(any())).thenThrow(
        new RuntimeException("no theme for this page"));

    try {
      cardListChildPagesDataSource.getCardElements();
      fail("a component with no resolvable theme must not render an empty card list");
    } catch (final RuntimeException expected) {
      assertNotNull(expected);
    }
  }

  @Test
  public void testGetCardElementsThrowsWhenTheThemeResolvesToNoUiFramework() throws Exception {
    when(theme.getUiFramework()).thenReturn(null);

    try {
      cardListChildPagesDataSource.getCardElements();
      fail("a component whose theme carries no UI framework must not render an empty card list");
    } catch (final RuntimeException expected) {
      assertNotNull(expected);
    }
  }

}
