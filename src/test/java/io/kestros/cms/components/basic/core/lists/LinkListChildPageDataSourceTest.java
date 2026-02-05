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
  public void testGetLinks() {
    assertEquals(3,
        linkListChildPageDataSource.getLinks().size());
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

}