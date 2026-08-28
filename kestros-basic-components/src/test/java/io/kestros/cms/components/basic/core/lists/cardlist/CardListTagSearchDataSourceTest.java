package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
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
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class CardListTagSearchDataSourceTest extends BaseDataSourceTest {

  private CardListTagSearchDataSource cardListTagSearchDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();
  private TagRetrievalService tagRetrievalService;

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    tagRetrievalService = mock(TagRetrievalService.class);
    context.registerService(TagRetrievalService.class, tagRetrievalService);
    // Before anything adapts: Sling Models resolves @OSGiService at the first adaptTo, so a service
    // registered later never appears on the model.
    registerAssetRetrievalService();

    setUpSampleCollection("/content/collection");
    setupSamplePage("/content/sessions", "/content/collection/asset-1");

    KestrosTag tag1 = mock(KestrosTag.class);
    when(tag1.getPath()).thenReturn("/etc/tags/topic/java");

    KestrosTag tag2 = mock(KestrosTag.class);
    when(tag2.getPath()).thenReturn("/etc/tags/topic/sling");

    // Use path-based matching since Resource instances may differ at runtime
    when(tagRetrievalService.getTagsOnResource(any(Resource.class)))
        .thenAnswer(new Answer<List<KestrosTag>>() {
          @Override
          public List<KestrosTag> answer(InvocationOnMock invocation) {
            Resource res = invocation.getArgument(0);
            String path = res.getPath();
            if ("/content/sessions/child-1".equals(path)) {
              return Arrays.asList(tag1, tag2);
            } else if ("/content/sessions/child-2".equals(path)) {
              return Arrays.asList(tag1);
            } else if ("/content/sessions/child-3".equals(path)) {
              return Collections.emptyList();
            }
            return Collections.emptyList();
          }
        });

    // The component resource lives on child-1, with tags configured for filtering
    properties.put("tags", new String[]{"/etc/tags/topic/java"});
    properties.put("readMoreText", "View Session");
    resource = context.create().resource("/content/sessions/child-1/jcr:content/component",
        properties);
    context.request().setResource(resource);
    cardListTagSearchDataSource = context.request().adaptTo(CardListTagSearchDataSource.class);
  }

  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> componentTypeProperties = new HashMap<>();
    componentTypeProperties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosCardList.RESOURCE_TYPE, componentTypeProperties);
    context.create().resource(KestrosCard.RESOURCE_TYPE, componentTypeProperties);
    context.create().resource(KestrosImage.RESOURCE_TYPE, componentTypeProperties);
    context.create().resource(KestrosButtonGroup.RESOURCE_TYPE, componentTypeProperties);
    context.create().resource(KestrosButton.RESOURCE_TYPE, componentTypeProperties);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(
        cardListTagSearchDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetCardElementsReturnsTagMatchedPages() {
    // child-1 is current page (excluded), child-2 shares tag1 (matched), child-3 has no tags
    assertEquals(1, cardListTagSearchDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElementsExcludesCurrentPage() {
    // child-1 is current page and should not appear in results even though it shares tags
    for (KestrosCard card : cardListTagSearchDataSource.getCardElements()) {
      assertNotNull(card);
    }
    assertEquals(1, cardListTagSearchDataSource.getCardElements().size());
  }

  @Test
  public void testGetReadMoreText() {
    assertEquals("View Session", cardListTagSearchDataSource.getReadMoreText());
  }

  @Test
  public void testGetTaggedPages() {
    assertEquals(1, cardListTagSearchDataSource.getTaggedPages().size());
  }

  @Test
  public void testGetContainingPage() {
    assertNotNull(cardListTagSearchDataSource.getContainingPage());
    assertEquals("/content/sessions/child-1",
        cardListTagSearchDataSource.getContainingPage().getPath());
  }

  @Test
  public void testGetRootPage() {
    assertNotNull(cardListTagSearchDataSource.getRootPage());
    assertEquals("/content/sessions",
        cardListTagSearchDataSource.getRootPage().getPath());
  }

  @Test
  public void testGetConfiguredTags() {
    String[] tags = cardListTagSearchDataSource.getConfiguredTags();
    assertEquals(1, tags.length);
    assertEquals("/etc/tags/topic/java", tags[0]);
  }

  @Test
  public void testGetConfiguredTagsWhenEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    emptyProps.put("readMoreText", "View");
    Resource emptyResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/empty-component", emptyProps);
    context.request().setResource(emptyResource);
    CardListTagSearchDataSource emptyDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(0, emptyDs.getConfiguredTags().length);
  }

  @Test
  public void testGetCardElementsWithSortByName() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "name");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource sortDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, sortDs.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithSortByTitle() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "title");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-title-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource sortDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, sortDs.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithSortByCreatedDate() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "createdDate");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-created-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource sortDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, sortDs.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithSortByLastModified() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "lastModified");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-modified-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource sortDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, sortDs.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithReverse() {
    Map<String, Object> reverseProps = new HashMap<>();
    reverseProps.put("tags", new String[]{"/etc/tags/topic/java"});
    reverseProps.put("readMoreText", "View Session");
    reverseProps.put("reverse", true);
    Resource reverseResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/reverse-component", reverseProps);
    context.request().setResource(reverseResource);
    CardListTagSearchDataSource reverseDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, reverseDs.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithLimit() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java", "/etc/tags/topic/sling"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "1");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource limitDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    List<KestrosCard> cards = limitDs.getCardElements();
    assertEquals(1, cards.size());
  }

  @Test
  public void testGetCardElementsWithInvalidLimit() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "abc");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/invalid-limit-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource limitDs =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, limitDs.getCardElements().size());
  }

  // ---------------------------------------------------------------------------------------------
  // The sort comparators only run when there are at least two matching pages. Placing the component
  // on child-3 (which carries no tags of its own) leaves both child-1 and child-2 in the result, so
  // the comparator bodies actually execute.
  // ---------------------------------------------------------------------------------------------

  private CardListTagSearchDataSource twoMatchesWith(final Map<String, Object> extraProperties) {
    final Map<String, Object> props = new HashMap<>(extraProperties);
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    props.put("readMoreText", "View Session");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/component-" + extraProperties.hashCode(), props);
    context.request().setResource(componentResource);
    return context.request().adaptTo(CardListTagSearchDataSource.class);
  }

  @Test
  public void testGetCardElementsSortsTwoPagesByName() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "name");

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  @Test
  public void testGetCardElementsSortsTwoPagesByTitle() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "title");

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  @Test
  public void testGetCardElementsSortsTwoPagesByCreatedDate() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "createdDate");

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  @Test
  public void testGetCardElementsSortsTwoPagesByLastModified() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "lastModified");

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  @Test
  public void testGetCardElementsReversesTwoPages() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "name");
    props.put("reverse", true);

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  @Test
  public void testGetCardElementsLimitsTwoPagesToOne() {
    final Map<String, Object> props = new HashMap<>();
    props.put("limit", "1");

    assertEquals(1, twoMatchesWith(props).getCardElements().size());
  }

  // ---------------------------------------------------------------------------------------------
  // Guard branches.
  // ---------------------------------------------------------------------------------------------

  /** pagesPath overrides the containing page's parent as the search root. */
  @Test
  public void testGetRootPathUsesTheConfiguredPagesPath() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    props.put("pagesPath", "/content/sessions");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/pages-path-component", props);
    context.request().setResource(componentResource);

    assertEquals("/content/sessions",
        context.request().adaptTo(CardListTagSearchDataSource.class).getRootPath());
  }

  @Test
  public void testGetRootPageWhenThePathDoesNotResolve() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    props.put("pagesPath", "/content/nowhere");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/missing-root-component", props);
    context.request().setResource(componentResource);

    assertNull(context.request().adaptTo(CardListTagSearchDataSource.class).getRootPage());
  }

  @Test
  public void testGetTaggedPagesWhenNoTagsAreConfigured() {
    final Map<String, Object> props = new HashMap<>();
    props.put("readMoreText", "View Session");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/no-tags-component", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(CardListTagSearchDataSource.class)
        .getTaggedPages().isEmpty());
  }

  @Test
  public void testGetTaggedPagesWhenTheRootPageDoesNotResolve() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    props.put("pagesPath", "/content/nowhere");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/no-root-component", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(CardListTagSearchDataSource.class)
        .getTaggedPages().isEmpty());
  }

  @Test
  public void testGetCardElementsWhenNothingMatches() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/nothing-has-this"});
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/no-match-component", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(CardListTagSearchDataSource.class)
        .getCardElements().isEmpty());
  }

  @Test
  public void testGetReadMoreTextWhenNotConfigured() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/no-read-more-component", props);
    context.request().setResource(componentResource);

    assertNull(context.request().adaptTo(CardListTagSearchDataSource.class).getReadMoreText());
  }

  /**
   * The date comparators guard on both the jcr:content child and the date property. The fixture
   * pages all have a jcr:content and neither date, so only one side of each guard was reached.
   */
  private void addTaggedPagesWithAndWithoutDates() {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/sessions/child-4", pageProperties);

    final Map<String, Object> datedContent = new HashMap<>();
    datedContent.put("jcr:primaryType", "nt:unstructured");
    datedContent.put("jcr:created", Calendar.getInstance());
    datedContent.put("jcr:lastModified", Calendar.getInstance());
    context.create().resource("/content/sessions/child-5", pageProperties);
    context.create().resource("/content/sessions/child-5/jcr:content", datedContent);
  }

  @Test
  public void testSortByCreatedDateAcrossPagesWithAndWithoutDates() {
    addTaggedPagesWithAndWithoutDates();
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "createdDate");

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  @Test
  public void testSortByLastModifiedAcrossPagesWithAndWithoutDates() {
    addTaggedPagesWithAndWithoutDates();
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "lastModified");

    assertEquals(2, twoMatchesWith(props).getCardElements().size());
  }

  /**
   * The other half of the fix. CardListTagSearchDataSource had to pass its AssetRetrievalService
   * too, and nothing tested it: reverting that data source alone left the whole suite green, which
   * is the same "passes whether the bug is present" fault the child-pages test was written to close.
   *
   * <p>The asset and the page carry deliberately different wording, so an assertion on the asset's
   * title cannot pass from the page's.
   */
  @Test
  public void testGetCardElementsResolvesTheAssetTitleAndDescriptionThroughTheDataSource() {
    // setupSamplePage points every child at /content/collection/asset-1, whose title and description
    // ("Asset 1 Title"/"Asset 1 Description") differ from the page's own ("Title"/"Description"), so
    // an assertion on the asset wording cannot pass from the page's.
    final List<KestrosCard> cards = cardListTagSearchDataSource.getCardElements();
    assertFalse("the tag search must match at least one page", cards.isEmpty());

    final KestrosImage image = cards.get(0).getImageElement();
    assertNotNull("a card built from a tagged page with an image must have an image element", image);
    assertEquals("alt text must come from the asset, not the page", "Asset 1 Title",
        image.getAltText());
    assertEquals("caption must come from the asset, not the page", "Asset 1 Description",
        image.getCaption());
  }

  // ---------------------------------------------------------------------------------------------
  // The same principle as the child-pages list. This one always skipped, but in complete silence:
  // it caught Exception and discarded it, with no log statement at all.
  // ---------------------------------------------------------------------------------------------

  /** Two healthy pages with titles that tell them apart, plus a root to hold them. */
  private void createDistinctlyTitledPages() {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/tagged", pageProperties);

    final Map<String, Object> firstContent = new HashMap<>();
    firstContent.put("jcr:primaryType", "nt:unstructured");
    firstContent.put("jcr:title", "First Tagged Page");
    context.create().resource("/content/tagged/good-1", pageProperties);
    context.create().resource("/content/tagged/good-1/jcr:content", firstContent);

    final Map<String, Object> secondContent = new HashMap<>();
    secondContent.put("jcr:primaryType", "nt:unstructured");
    secondContent.put("jcr:title", "Second Tagged Page");
    context.create().resource("/content/tagged/good-2", pageProperties);
    context.create().resource("/content/tagged/good-2/jcr:content", secondContent);
  }

  private BaseContentPage pageAt(final String path) {
    final Resource pageResource = context.resourceResolver().getResource(path);
    assertNotNull("the fixture must create " + path, pageResource);
    final BaseContentPage page = pageResource.adaptTo(BaseContentPage.class);
    assertNotNull(path + " must adapt to a page", page);
    return page;
  }

  private BaseContentPage brokenPage(final String path, final RuntimeException failure) {
    final BaseContentPage broken = mock(BaseContentPage.class);
    when(broken.getPath()).thenReturn(path);
    when(broken.getName()).thenReturn(path.substring(path.lastIndexOf('/') + 1));
    when(broken.getDisplayDescription()).thenThrow(failure);
    return broken;
  }

  /** A data source whose tag search matches exactly the pages given, in that order. */
  private CardListTagSearchDataSource dataSourceOver(final List<BaseContentPage> matches) {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    props.put("readMoreText", "View Session");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/comp-mixed-" + matches.size(), props);
    context.request().setResource(componentResource);

    final CardListTagSearchDataSource dataSource = spy(
        context.request().adaptTo(CardListTagSearchDataSource.class));
    doReturn(new ArrayList<>(matches)).when(dataSource).getTaggedPages();
    return dataSource;
  }

  @Test
  public void testGetCardElementsSkipsTheBrokenPageAndKeepsTheRest() {
    createDistinctlyTitledPages();
    final CardListTagSearchDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/tagged/good-1"),
        brokenPage("/content/tagged/broken", new IllegalStateException("jcr:content will not adapt")),
        pageAt("/content/tagged/good-2")));

    final List<KestrosCard> cards = dataSource.getCardElements();

    assertEquals("the two healthy pages must still produce cards", 2, cards.size());
    assertEquals("good-1", cards.get(0).getForcedResourceName());
    assertEquals("good-2", cards.get(1).getForcedResourceName());
    assertEquals("First Tagged Page", cards.get(0).getTitleElement().getHeadingText());
    assertEquals("Second Tagged Page", cards.get(1).getTitleElement().getHeadingText());
  }

  @Test
  public void testGetCardElementsLogsTheSkippedPageWithTheExceptionClassAndMessage() {
    createDistinctlyTitledPages();
    final CardListTagSearchDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/tagged/good-1"),
        brokenPage("/content/tagged/broken", new IllegalStateException("jcr:content will not adapt"))));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListTagSearchDataSource.class)) {
      assertEquals(1, dataSource.getCardElements().size());

      assertEquals("skipping in silence is the defect; exactly one warning is expected", 1,
          warnings.messages().size());
      final String warning = warnings.messages().get(0);
      assertTrue("the warning must name the skipped page: " + warning,
          warning.contains("/content/tagged/broken"));
      assertTrue("the warning must name the exception class: " + warning,
          warning.contains("java.lang.IllegalStateException"));
      assertTrue("the warning must carry the exception message: " + warning,
          warning.contains("jcr:content will not adapt"));
    }
  }

  @Test
  public void testGetCardElementsNamesTheExceptionClassWhenThereIsNoMessage() {
    createDistinctlyTitledPages();
    final CardListTagSearchDataSource dataSource = dataSourceOver(Arrays.asList(
        pageAt("/content/tagged/good-1"),
        brokenPage("/content/tagged/broken", new NullPointerException())));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListTagSearchDataSource.class)) {
      assertEquals(1, dataSource.getCardElements().size());

      assertEquals(1, warnings.messages().size());
      assertTrue("the warning must name the exception class even with a null message: "
          + warnings.messages().get(0),
          warnings.messages().get(0).contains("java.lang.NullPointerException"));
    }
  }

  @Test
  public void testGetCardElementsThrowsWhenTheThemeCannotBeResolved() throws Exception {
    when(themeProviderService.getThemeForPage(any())).thenThrow(
        new RuntimeException("no theme for this page"));

    try {
      cardListTagSearchDataSource.getCardElements();
      fail("a component with no resolvable theme must not render an empty card list");
    } catch (final RuntimeException expected) {
      assertNotNull(expected);
    }
  }

  @Test
  public void testGetCardElementsThrowsWhenTheThemeResolvesToNoUiFramework() throws Exception {
    when(theme.getUiFramework()).thenReturn(null);

    try {
      cardListTagSearchDataSource.getCardElements();
      fail("a component whose theme carries no UI framework must not render an empty card list");
    } catch (final RuntimeException expected) {
      assertNotNull(expected);
    }
  }
}
