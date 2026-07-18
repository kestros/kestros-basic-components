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
}
