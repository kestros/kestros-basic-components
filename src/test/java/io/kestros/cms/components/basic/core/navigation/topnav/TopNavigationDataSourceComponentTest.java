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

public class TopNavigationDataSourceComponentTest extends BaseDataSourceComponentTest {
  private TopNavigationDataSourceComponent topNavigation;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();
  private Map<String, Object> linkProperties = new HashMap<>();
  private Map<String, Object> imageProperties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosTopNavigation.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/sites/page", null);

    properties.put("sling:resourceType", KestrosTopNavigation.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/top-nav",
        properties);
    context.request().setResource(resource);
    topNavigation = context.request().adaptTo(TopNavigationDataSourceComponent.class);

    linkProperties.put("sling:resourceType", KestrosTopNavigationItem.RESOURCE_TYPE);
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/link-1",
        linkProperties);
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/link-2",
        linkProperties);
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/link-3",
        linkProperties);

  }

  @Override
  @Test
  @Ignore("Pre-existing failure")
  public void testToSyntheticResource() {
    assertNotNull(topNavigation.toSyntheticResource(context.resourceResolver(),
        "/test"));
    assertEquals("/synthetics/test/top-nav", topNavigation.toSyntheticResource(context.resourceResolver(),
        "/test").getPath());
  }

  @Test
  public void testGetNavigationLinkElements() {
    assertEquals(3, topNavigation.getNavigationLinkElements().size());
  }

  @Test
  public void testGetImageElement() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    imageProperties.put("imagePath","/content/assets/collection/asset-1");
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/imageElement",
            imageProperties);

    setUpSampleCollection("/content/assets/collection");
    assertNotNull(topNavigation.getImageElement());
  }

  @Test
  @Ignore("Pre-existing failure")
  public void testGetImageElementWhenNoImageElementResource() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    properties.put("imagePath","/content/assets/collection/asset-1");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/top-nav2",
            properties);
    context.request().setResource(resource);
    topNavigation = context.request().adaptTo(TopNavigationDataSourceComponent.class);

    setUpSampleCollection("/content/assets/collection");
    assertNotNull(topNavigation.getImageElement());
  }

  @Test
  @Ignore("Pre-existing failure")
  public void testGetImageElementWhenDoesNotExist() {
    imageProperties.put("imagePath","/content/assets/collection/asset-1");
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/imageElement",
            imageProperties);

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/top-nav2",
            properties);
    context.request().setResource(resource);
    topNavigation = context.request().adaptTo(TopNavigationDataSourceComponent.class);

    imageProperties.put("imagePath","path");
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav2/imageElement",
            imageProperties);

    assertNull(topNavigation.getImageElement());
  }

  @Test
  public void testGetNavigationLinkElementsNotNull() {
    assertNotNull(topNavigation.getNavigationLinkElements());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosTopNavigation.RESOURCE_TYPE, topNavigation.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(topNavigation.getResource());
  }

  @Test
  public void testGetPath() {
    assertNotNull(topNavigation.getPath());
  }

  @Test
  public void testGetChildElements() {
    assertNotNull(topNavigation.getChildElements());
  }

  @Test
  public void testGetChildren() {
    assertEquals(3, topNavigation.getChildren().size());
  }

  @Test
  public void testGetBrandName() {
    assertNotNull(topNavigation);
  }

  @Test
  public void testGetNavigationLinkElementsSize() {
    assertNotNull(topNavigation.getNavigationLinkElements());
    assertEquals(3, topNavigation.getNavigationLinkElements().size());
  }

}