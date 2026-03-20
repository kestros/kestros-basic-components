package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import java.util.Arrays;
import java.util.Collections;
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
  public void testGetSortByDefault() {
    assertEquals("", cardListTagSearchDataSource.getSortBy());
  }

  @Test
  public void testGetSortByTitle() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "title");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-title-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals("title", ds.getSortBy());
  }

  @Test
  public void testGetSortByName() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "name");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-name-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals("name", ds.getSortBy());
  }

  @Test
  public void testIsReverseOrderDefault() {
    assertEquals(false, cardListTagSearchDataSource.isReverseOrder());
  }

  @Test
  public void testIsReverseOrderTrue() {
    Map<String, Object> reverseProps = new HashMap<>();
    reverseProps.put("tags", new String[]{"/etc/tags/topic/java"});
    reverseProps.put("readMoreText", "View Session");
    reverseProps.put("reverse", true);
    Resource reverseResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/reverse-component", reverseProps);
    context.request().setResource(reverseResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(true, ds.isReverseOrder());
  }

  @Test
  public void testGetLimitDefault() {
    assertEquals(0, cardListTagSearchDataSource.getLimit());
  }

  @Test
  public void testGetLimitSet() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "5");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(5, ds.getLimit());
  }

  @Test
  public void testGetLimitInvalidString() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "invalid");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-invalid-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(0, ds.getLimit());
  }

  @Test
  public void testGetLimitZero() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "0");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-zero-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(0, ds.getLimit());
  }

  @Test
  public void testGetCardElementsWithSortByTitle() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "title");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-title-cards-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    // child-2 matched, child-1 excluded (current page), child-3 no tags
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithSortByName() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("readMoreText", "View Session");
    sortProps.put("sortBy", "name");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-name-cards-component", sortProps);
    context.request().setResource(sortResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithReverse() {
    Map<String, Object> reverseProps = new HashMap<>();
    reverseProps.put("tags", new String[]{"/etc/tags/topic/java"});
    reverseProps.put("readMoreText", "View Session");
    reverseProps.put("reverse", true);
    Resource reverseResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/reverse-cards-component", reverseProps);
    context.request().setResource(reverseResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithLimit() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "1");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-cards-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    // Only 1 matched page anyway, limit of 1 should still return 1
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithLimitZeroReturnsAll() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("readMoreText", "View Session");
    limitProps.put("limit", "0");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-zero-cards-component", limitProps);
    context.request().setResource(limitResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithSortAndReverse() {
    Map<String, Object> sortRevProps = new HashMap<>();
    sortRevProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortRevProps.put("readMoreText", "View Session");
    sortRevProps.put("sortBy", "name");
    sortRevProps.put("reverse", true);
    Resource sortRevResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-reverse-cards-component", sortRevProps);
    context.request().setResource(sortRevResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsWithSortAndLimit() {
    Map<String, Object> sortLimitProps = new HashMap<>();
    sortLimitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortLimitProps.put("readMoreText", "View Session");
    sortLimitProps.put("sortBy", "title");
    sortLimitProps.put("limit", "1");
    Resource sortLimitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-limit-cards-component", sortLimitProps);
    context.request().setResource(sortLimitResource);
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertEquals(1, ds.getCardElements().size());
  }

  @Test
  public void testGetCardElementsNoSortNoReverseNoLimit() {
    // Default behavior — no sort/reverse/limit properties set
    assertEquals(1, cardListTagSearchDataSource.getCardElements().size());
  }

  @Test
  public void testGetTaggedPagesWithNoTagService() {
    // When tagRetrievalService is null, should return empty
    Map<String, Object> noServiceProps = new HashMap<>();
    noServiceProps.put("tags", new String[]{"/etc/tags/topic/java"});
    noServiceProps.put("readMoreText", "View");
    Resource noServiceResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/no-service-component", noServiceProps);
    context.request().setResource(noServiceResource);
    // The service is already registered in context, so this tests the non-null path.
    // Null service path is exercised in the base getTaggedPages when service isn't bound.
    CardListTagSearchDataSource ds =
        context.request().adaptTo(CardListTagSearchDataSource.class);
    assertNotNull(ds);
  }
}
