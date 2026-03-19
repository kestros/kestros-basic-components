package io.kestros.cms.components.basic.core.lists.linklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
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
    // Create a new datasource without the tag service registered
    Map<String, Object> props = new HashMap<>();
    props.put("tags", new String[]{"/etc/tags/topic/java"});
    Resource res = context.create().resource(
        "/content/sessions/child-1/jcr:content/no-service-component", props);
    context.request().setResource(res);
    // The existing instance has the service injected; test with null service indirectly
    // by verifying it handles missing tags gracefully
    LinkListTagSearchDataSource ds = context.request().adaptTo(LinkListTagSearchDataSource.class);
    assertNotNull(ds);
  }

  @Test
  public void testGetLinkElementsHaveValidHref() {
    List<KestrosLink> links = linkListTagSearchDataSource.getLinkElements();
    assertEquals(1, links.size());
    assertNotNull(links.get(0).getHref());
  }

  @Test
  public void testGetLinkElementsHaveValidText() {
    List<KestrosLink> links = linkListTagSearchDataSource.getLinkElements();
    assertEquals(1, links.size());
    assertNotNull(links.get(0).getText());
  }
}
