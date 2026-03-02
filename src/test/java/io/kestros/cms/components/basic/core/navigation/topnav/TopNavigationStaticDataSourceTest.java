package io.kestros.cms.components.basic.core.navigation.topnav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class TopNavigationStaticDataSourceTest extends BaseDataSourceComponentTest {

  private TopNavigationStaticDataSource topNavStatic;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosTopNavigation.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/sites/nav-page", null);
    properties.put("sling:resourceType", KestrosTopNavigation.RESOURCE_TYPE);
    resource = context.create().resource("/content/sites/nav-page/child-1/jcr:content/top-nav",
        properties);
    context.request().setResource(resource);
    topNavStatic = context.request().adaptTo(TopNavigationStaticDataSource.class);
  }

  @Override
  @Test
  @Ignore("Pre-existing failure")
  public void testToSyntheticResource() {
    // Not applicable
  }

  @Test
  public void testGetNavigationLinkElementsEmpty() {
    assertNotNull(topNavStatic.getNavigationLinkElements());
    assertEquals(0, topNavStatic.getNavigationLinkElements().size());
  }

  @Test
  public void testGetNavigationLinkElementsWithItems() {
    Map<String, Object> itemProps = new HashMap<>();
    itemProps.put("sling:resourceType", KestrosTopNavigationItem.RESOURCE_TYPE);
    context.create().resource(
        "/content/sites/nav-page/child-1/jcr:content/top-nav/link-1", itemProps);
    context.create().resource(
        "/content/sites/nav-page/child-1/jcr:content/top-nav/link-2", itemProps);

    topNavStatic = context.request().adaptTo(TopNavigationStaticDataSource.class);
    assertEquals(2, topNavStatic.getNavigationLinkElements().size());
  }

  @Test
  public void testGetBrandName() {
    assertNull(topNavStatic.getBrandName());
  }

  @Test
  public void testGetBrandNameWhenSet() {
    Map<String, Object> props = new HashMap<>();
    props.put("sling:resourceType", KestrosTopNavigation.RESOURCE_TYPE);
    props.put("brandName", "My Brand");
    Resource r = context.create().resource(
        "/content/sites/nav-page/child-2/jcr:content/top-nav-with-brand", props);
    context.request().setResource(r);
    TopNavigationStaticDataSource nav = context.request().adaptTo(
        TopNavigationStaticDataSource.class);
    assertEquals("My Brand", nav.getBrandName());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosTopNavigation.RESOURCE_TYPE, topNavStatic.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(topNavStatic.getResource());
  }

  @Test
  public void testGetPath() {
    assertNotNull(topNavStatic.getPath());
  }

  @Test
  public void testGetChildren() {
    assertNotNull(topNavStatic.getChildren());
  }

  @Test
  public void testGetNavigationLinks() {
    assertNotNull(topNavStatic.getNavigationLinks());
    assertEquals(0, topNavStatic.getNavigationLinks().size());
  }

  @Test
  @Ignore("Source code NPE when no image child resource exists")
  public void testGetImageElementWhenNoImageElement() {
    // TopNavigationStaticDataSource.getImageElement() has a null ref bug when
    // neither 'imageElement' nor 'image' child resource exists.
    assertNull(topNavStatic.getImageElement());
  }

  @Test
  public void testGetImageElementWhenImageElement() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    setUpSampleCollection("/content/assets/collection");

    Map<String, Object> imageProps = new HashMap<>();
    imageProps.put("imagePath", "/content/assets/collection/asset-1");
    context.create().resource(
        "/content/sites/nav-page/child-1/jcr:content/top-nav/imageElement", imageProps);

    topNavStatic = context.request().adaptTo(TopNavigationStaticDataSource.class);
    assertNotNull(topNavStatic.getImageElement());
  }
}
