package io.kestros.cms.components.basic.core.navigation.topnav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
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

    properties.put("sling:resourceType", KestrosNavigation.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/top-nav",
        properties);
    context.request().setResource(resource);
    topNavigation = context.request().adaptTo(TopNavigationDataSourceComponent.class);

    linkProperties.put("sling:resourceType", KestrosLink.RESOURCE_TYPE);
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/link-1",
        linkProperties);
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/link-2",
        linkProperties);
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/link-3",
        linkProperties);


    imageProperties.put("imagePath","path");
    context.create().resource("/content/sites/page/child-3/jcr:content/top-nav/imageElement",
        imageProperties);

  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(topNavigation.toSyntheticResource(context.resourceResolver(),
        "/test"));
    assertEquals("/synthetics/test/top-nav", topNavigation.toSyntheticResource(context.resourceResolver(),
        "/test").getPath());
  }

  @Test
  public void testGetNavigationLinks() {

    assertEquals(3, topNavigation.getNavigationLinks().size());
  }


  @Test
  public void testGetLogo() {
    assertNotNull(topNavigation.getLogo());
  }

}