package io.kestros.cms.components.basic.core.lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.components.basic.core.lists.linklist.LinkListChildPageDataSource;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class LinkListChildPageDataSourceTest extends BaseDataSourceTest {
  private LinkListChildPageDataSource linkListChildPageDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/sites/page", null);
    properties.put("pagesPath", "/content/sites/page");
    resource = context.create().resource("/content/linklist/childpages/datasource", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(
        linkListChildPageDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
    assertEquals("/synthetics/test/datasource",
        linkListChildPageDataSource.toSyntheticResource(context.resourceResolver(),
            "/test").getPath());
    assertEquals("/libs/kestros/commons/components/lists/link-list",
        linkListChildPageDataSource.toSyntheticResource(context.resourceResolver(),
            "/test").getResourceType());

  }

  @Test
  public void testGetRootPath() {
    assertEquals("/content/sites/page", linkListChildPageDataSource.getRootPath());
  }

  @Test
  public void testGetTarget() {
    assertEquals("_self", linkListChildPageDataSource.getTarget().getTargetValue());
  }

  @Test
  public void testGetLinkElements() {
    assertEquals(3,
        linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetComponentVariationRetrievalService() {
    assertNotNull(
        linkListChildPageDataSource.getComponentVariationRetrievalService());
  }

  @Test
  public void testGetComponentUiFrameworkViewRetrievalService() {
    assertNotNull(
        linkListChildPageDataSource.getComponentUiFrameworkViewRetrievalService());
  }

  @Test
  public void testGetLinkElements_defaultSortBy_returnsNaturalOrder() {
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByEmpty_returnsAll() {
    properties.put("sortBy", "");
    resource = context.create().resource("/content/linklist/childpages/ds-sortby-empty",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByTitle() {
    properties.put("sortBy", "title");
    resource = context.create().resource("/content/linklist/childpages/ds-sortby-title",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByName() {
    properties.put("sortBy", "name");
    resource = context.create().resource("/content/linklist/childpages/ds-sortby-name",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_reverseOrder() {
    properties.put("reverse", true);
    resource = context.create().resource("/content/linklist/childpages/ds-reverse", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_reverseOrderDefault_false() {
    resource = context.create().resource("/content/linklist/childpages/ds-no-reverse", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitApplied() {
    properties.put("limit", "2");
    resource = context.create().resource("/content/linklist/childpages/ds-limit-2", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(2, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitZero_returnsAll() {
    properties.put("limit", "0");
    resource = context.create().resource("/content/linklist/childpages/ds-limit-0", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitDefault_returnsAll() {
    resource = context.create().resource("/content/linklist/childpages/ds-no-limit", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitInvalidString_returnsAll() {
    properties.put("limit", "abc");
    resource = context.create().resource("/content/linklist/childpages/ds-limit-invalid",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByNameAndReverse() {
    properties.put("sortBy", "name");
    properties.put("reverse", true);
    resource = context.create().resource("/content/linklist/childpages/ds-name-reverse",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByTitleAndLimit() {
    properties.put("sortBy", "title");
    properties.put("limit", "1");
    resource = context.create().resource("/content/linklist/childpages/ds-title-limit", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(1, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByCreated_doesNotCrash() {
    properties.put("sortBy", "created");
    resource = context.create().resource("/content/linklist/childpages/ds-sortby-created",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByModified_doesNotCrash() {
    properties.put("sortBy", "modified");
    resource = context.create().resource("/content/linklist/childpages/ds-sortby-modified",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByCreatedReversed() {
    properties.put("sortBy", "created");
    properties.put("reverse", true);
    resource = context.create().resource("/content/linklist/childpages/ds-created-reverse",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByModifiedWithLimit() {
    properties.put("sortBy", "modified");
    properties.put("limit", "2");
    resource = context.create().resource("/content/linklist/childpages/ds-modified-limit",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(2, linkListChildPageDataSource.getLinkElements().size());
  }

}