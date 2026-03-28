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
  public void testToSyntheticResource() {
    registerAssetRetrievalService();
    Map<String, Object> navProps = new HashMap<>();
    navProps.put("sling:resourceType", KestrosTopNavigation.RESOURCE_TYPE);
    navProps.put("kes:datasource", "default");
    Resource navResource = context.create().resource(
        "/content/sites/page/child-3/jcr:content/top-nav-no-children", navProps);
    Map<String, Object> imgProps = new HashMap<>();
    imgProps.put("imagePath", "/content/sites/page");
    imgProps.put("sling:resourceType", "kestros/commons/components/content/image");
    context.create().resource(
        "/content/sites/page/child-3/jcr:content/top-nav-no-children/imageElement", imgProps);
    context.request().setResource(navResource);
    TopNavigationDataSourceComponent navNoChildren = context.request().adaptTo(
        TopNavigationDataSourceComponent.class);
    assertNotNull(navNoChildren.toSyntheticResource(context.resourceResolver(), "/test"));
    assertEquals("/synthetics/test/top-nav-no-children",
        navNoChildren.toSyntheticResource(context.resourceResolver(), "/test").getPath());
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
  public void testGetImageElementWhenNoImageElementResource() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    properties.put("imagePath","/content/assets/collection/asset-1");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/top-nav2",
            properties);
    context.request().setResource(resource);
    topNavigation = context.request().adaptTo(TopNavigationDataSourceComponent.class);

    setUpSampleCollection("/content/assets/collection");
    assertNull(topNavigation.getImageElement());
  }

  @Test
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

}