package io.kestros.cms.components.basic.core.lists.linklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class LinkListChildPageDataSourceTest extends BaseDataSourceTest {

  private LinkListChildPageDataSource linkListChildPageDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/collection");
    setupSamplePage("/content/page", "/content/collection/asset-1");

    properties.put("pagesPath", "/content/page");
    resource = context.create().resource("/content/page/jcr:content/component", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
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
        linkListChildPageDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetRootPath() {
    assertEquals("/content/page", linkListChildPageDataSource.getRootPath());
  }

  @Test
  public void testGetLinkElements() {
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByName() {
    properties.put("sortBy", "name");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-name", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    List<KestrosLink> links = linkListChildPageDataSource.getLinkElements();
    assertEquals(3, links.size());
  }

  @Test
  public void testGetLinkElements_sortByTitle() {
    properties.put("sortBy", "title");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-title", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    List<KestrosLink> links = linkListChildPageDataSource.getLinkElements();
    assertEquals(3, links.size());
  }

  @Test
  public void testGetLinkElements_sortByCreatedDate() {
    properties.put("sortBy", "createdDate");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-created",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    List<KestrosLink> links = linkListChildPageDataSource.getLinkElements();
    assertEquals(3, links.size());
  }

  @Test
  public void testGetLinkElements_sortByLastModified() {
    properties.put("sortBy", "lastModified");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-modified",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    List<KestrosLink> links = linkListChildPageDataSource.getLinkElements();
    assertEquals(3, links.size());
  }

  @Test
  public void testGetLinkElements_sortByEmpty_returnsNaturalOrder() {
    properties.put("sortBy", "");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-empty", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_reverseOrder() {
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-reverse", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitApplied() {
    properties.put("limit", "2");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-2", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(2, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitZero_returnsAll() {
    properties.put("limit", "0");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-0", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitDefault_returnsAll() {
    resource = context.create().resource("/content/page/jcr:content/comp-no-limit", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_limitInvalidString_returnsAll() {
    properties.put("limit", "invalid");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-invalid",
        properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByNameAndReverse() {
    properties.put("sortBy", "name");
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-name-reverse", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(3, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_sortByTitleAndLimit() {
    properties.put("sortBy", "title");
    properties.put("limit", "1");
    resource = context.create().resource("/content/page/jcr:content/comp-title-limit", properties);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(1, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_nullRootPath_returnsEmpty() {
    Map<String, Object> emptyProps = new HashMap<>();
    resource = context.create().resource("/content/page/jcr:content/comp-no-path", emptyProps);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(0, linkListChildPageDataSource.getLinkElements().size());
  }

  @Test
  public void testGetLinkElements_invalidRootPath_returnsEmpty() {
    Map<String, Object> invalidProps = new HashMap<>();
    invalidProps.put("pagesPath", "/content/nonexistent");
    resource = context.create().resource("/content/page/jcr:content/comp-invalid-path",
        invalidProps);
    context.request().setResource(resource);
    linkListChildPageDataSource = context.request().adaptTo(LinkListChildPageDataSource.class);
    assertEquals(0, linkListChildPageDataSource.getLinkElements().size());
  }

  /**
   * The date comparators read jcr:created / jcr:lastModified off each page's jcr:content, guarding
   * on both the child and the property. The sample pages all have a jcr:content and neither date,
   * so only one side of each guard was reached.
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

  private LinkListChildPageDataSource adaptWithSort(final String sortBy, final String name) {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagesPath", "/content/page");
    props.put("sortBy", sortBy);
    final Resource componentResource =
        context.create().resource("/content/page/jcr:content/" + name, props);
    context.request().setResource(componentResource);
    return context.request().adaptTo(LinkListChildPageDataSource.class);
  }

  @Test
  public void testGetLinkElementsSortByCreatedDateAcrossPagesWithAndWithoutDates() {
    addPagesWithAndWithoutDates();

    assertEquals(5, adaptWithSort("createdDate", "comp-created-mixed").getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsSortByLastModifiedAcrossPagesWithAndWithoutDates() {
    addPagesWithAndWithoutDates();

    assertEquals(5, adaptWithSort("lastModified", "comp-modified-mixed").getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsSortByName() {
    assertEquals(3, adaptWithSort("name", "comp-name").getLinkElements().size());
  }

  @Test
  public void testGetLinkElementsSortByTitle() {
    assertEquals(3, adaptWithSort("title", "comp-title").getLinkElements().size());
  }
}
