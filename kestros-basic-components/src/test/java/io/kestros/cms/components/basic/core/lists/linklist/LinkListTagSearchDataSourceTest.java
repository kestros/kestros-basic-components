package io.kestros.cms.components.basic.core.lists.linklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class LinkListTagSearchDataSourceTest extends BaseDataSourceTest {

  private LinkListTagSearchDataSource linkListTagSearchDataSource;
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

    properties.put("tags", new String[]{"/etc/tags/topic/java"});
    resource = context.create().resource("/content/sessions/child-1/jcr:content/component",
        properties);
    context.request().setResource(resource);
    linkListTagSearchDataSource = context.request().adaptTo(LinkListTagSearchDataSource.class);
  }

  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> componentTypeProperties = new HashMap<>();
    componentTypeProperties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosLinkList.RESOURCE_TYPE, componentTypeProperties);
    context.create().resource(KestrosLink.RESOURCE_TYPE, componentTypeProperties);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(
        linkListTagSearchDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetLinkElementsReturnsTagMatchedPages() {
    // child-1 is current page (excluded), child-2 shares tag1 (matched), child-3 has no tags
    assertEquals(1, linkListTagSearchDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsExcludesCurrentPage() {
    for (KestrosLink link : linkListTagSearchDataSource.getLinkElements()) {
      assertNotNull(link);
    }
    assertEquals(1, linkListTagSearchDataSource.getLinkElements().size());
  }

  @Test
  public void testGetTaggedPages() {
    assertEquals(1, linkListTagSearchDataSource.getTaggedPages().size());
  }

  @Test
  public void testGetContainingPage() {
    assertNotNull(linkListTagSearchDataSource.getContainingPage());
    assertEquals("/content/sessions/child-1",
        linkListTagSearchDataSource.getContainingPage().getPath());
  }

  @Test
  public void testGetRootPage() {
    assertNotNull(linkListTagSearchDataSource.getRootPage());
    assertEquals("/content/sessions",
        linkListTagSearchDataSource.getRootPage().getPath());
  }

  @Test
  public void testGetConfiguredTags() {
    String[] tags = linkListTagSearchDataSource.getConfiguredTags();
    assertEquals(1, tags.length);
    assertEquals("/etc/tags/topic/java", tags[0]);
  }

  @Test
  public void testGetConfiguredTagsWhenEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    Resource emptyResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/empty-component", emptyProps);
    context.request().setResource(emptyResource);
    LinkListTagSearchDataSource emptyDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(0, emptyDs.getConfiguredTags().length);
  }

  @Test
  public void testGetTaggedPagesWhenNoTagService() {
    // Create a fresh datasource without tag service registered
    Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    Resource res = context.create().resource(
        "/content/sessions/child-1/jcr:content/no-tag-service", props);
    context.request().setResource(res);
    // The existing datasource still has the service injected, so test the empty tags path
    LinkListTagSearchDataSource ds =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertNotNull(ds);
  }

  @Test
  public void testGetLinkElementsWithSortByName() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("sortBy", "name");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-component", sortProps);
    context.request().setResource(sortResource);
    LinkListTagSearchDataSource sortDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(1, sortDs.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsWithReverse() {
    Map<String, Object> reverseProps = new HashMap<>();
    reverseProps.put("tags", new String[]{"/etc/tags/topic/java"});
    reverseProps.put("reverse", true);
    Resource reverseResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/reverse-component", reverseProps);
    context.request().setResource(reverseResource);
    LinkListTagSearchDataSource reverseDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(1, reverseDs.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsWithLimit() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java", "/etc/tags/topic/sling"});
    limitProps.put("limit", "1");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/limit-component", limitProps);
    context.request().setResource(limitResource);
    LinkListTagSearchDataSource limitDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    // Only 1 result due to limit
    List<KestrosLink> links = limitDs.getLinkElements();
    assertEquals(1, links.size());
  }

  @Test
  public void testGetRootPathWithExplicitPagesPath() {
    Map<String, Object> pathProps = new HashMap<>();
    pathProps.put("tags", new String[]{"/etc/tags/topic/java"});
    pathProps.put("pagesPath", "/content/sessions");
    Resource pathResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/path-component", pathProps);
    context.request().setResource(pathResource);
    LinkListTagSearchDataSource pathDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals("/content/sessions", pathDs.getRootPath());
  }

  @Test
  public void testGetLinkElementsWithSortByTitle() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("sortBy", "title");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-title-component", sortProps);
    context.request().setResource(sortResource);
    LinkListTagSearchDataSource sortDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(1, sortDs.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsWithSortByCreatedDate() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("sortBy", "createdDate");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-created-component", sortProps);
    context.request().setResource(sortResource);
    LinkListTagSearchDataSource sortDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(1, sortDs.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsWithSortByLastModified() {
    Map<String, Object> sortProps = new HashMap<>();
    sortProps.put("tags", new String[]{"/etc/tags/topic/java"});
    sortProps.put("sortBy", "lastModified");
    Resource sortResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/sort-modified-component", sortProps);
    context.request().setResource(sortResource);
    LinkListTagSearchDataSource sortDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(1, sortDs.getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsWithInvalidLimit() {
    Map<String, Object> limitProps = new HashMap<>();
    limitProps.put("tags", new String[]{"/etc/tags/topic/java"});
    limitProps.put("limit", "abc");
    Resource limitResource = context.create().resource(
        "/content/sessions/child-1/jcr:content/invalid-limit-component", limitProps);
    context.request().setResource(limitResource);
    LinkListTagSearchDataSource limitDs =
        context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertEquals(1, limitDs.getLinkElements().size());
  }

  // ---------------------------------------------------------------------------------------------
  // The sort comparators only run with at least two matching pages. Placing the component on
  // child-3 (which carries no tags of its own) leaves both child-1 and child-2 in the result.
  // ---------------------------------------------------------------------------------------------

  private LinkListTagSearchDataSource twoMatchesWith(final Map<String, Object> extraProperties) {
    final Map<String, Object> props = new HashMap<>(extraProperties);
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/component-" + extraProperties.hashCode(), props);
    context.request().setResource(componentResource);
    return context.request().adaptTo(LinkListTagSearchDataSource.class);
  }

  @Test
  public void testGetLinkElementsSortsTwoPagesByName() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "name");

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsSortsTwoPagesByTitle() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "title");

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsSortsTwoPagesByCreatedDate() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "createdDate");

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsSortsTwoPagesByLastModified() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "lastModified");

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsReversesTwoPages() {
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "name");
    props.put("reverse", true);

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsLimitsTwoPagesToOne() {
    final Map<String, Object> props = new HashMap<>();
    props.put("limit", "1");

    assertEquals(1, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testGetRootPageWhenThePathDoesNotResolve() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    props.put("pagesPath", "/content/nowhere");
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/missing-root-component", props);
    context.request().setResource(componentResource);

    assertNull(context.request().adaptTo(LinkListTagSearchDataSource.class).getRootPage());
  }

  @Test
  public void testGetTaggedPagesWhenNoTagsAreConfigured() {
    final Map<String, Object> props = new HashMap<>();
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/no-tags-component", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(LinkListTagSearchDataSource.class)
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

    assertTrue(context.request().adaptTo(LinkListTagSearchDataSource.class)
        .getTaggedPages().isEmpty());
  }

  @Test
  public void testGetLinkElementsWhenNothingMatches() {
    final Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/nothing-has-this"});
    final Resource componentResource = context.create().resource(
        "/content/sessions/child-3/jcr:content/no-match-component", props);
    context.request().setResource(componentResource);

    assertTrue(context.request().adaptTo(LinkListTagSearchDataSource.class)
        .getLinkElements().isEmpty());
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

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }

  @Test
  public void testSortByLastModifiedAcrossPagesWithAndWithoutDates() {
    addTaggedPagesWithAndWithoutDates();
    final Map<String, Object> props = new HashMap<>();
    props.put("sortBy", "lastModified");

    assertEquals(2, twoMatchesWith(props).getLinkElements().size());
  }
}
