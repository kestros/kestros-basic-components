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

package io.kestros.cms.components.basic.core.navigation.nav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class NavigationDataSourceComponentTest extends BaseDataSourceComponentTest {
  private NavigationDataSourceComponent navigation;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();
  private Map<String, Object> linkProperties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosNavigation.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/sites/page", null);

    properties.put("sling:resourceType", KestrosNavigation.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/nav",
        properties);
    context.request().setResource(resource);

    linkProperties.put("sling:resourceType", KestrosNavigationItem.RESOURCE_TYPE);
    context.create().resource("/content/sites/page/child-3/jcr:content/nav/link-1",
        linkProperties);
    context.create().resource("/content/sites/page/child-3/jcr:content/nav/link-2",
        linkProperties);
    context.create().resource("/content/sites/page/child-3/jcr:content/nav/link-3",
        linkProperties);

    navigation = context.request().adaptTo(NavigationDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    Map<String, Object> navProps = new HashMap<>();
    navProps.put("sling:resourceType", KestrosNavigation.RESOURCE_TYPE);
    navProps.put("kes:datasource", "default");
    Resource navResource = context.create().resource(
        "/content/sites/page/child-3/jcr:content/nav-no-children", navProps);
    context.request().setResource(navResource);
    NavigationDataSourceComponent navNoChildren = context.request().adaptTo(
        NavigationDataSourceComponent.class);
    assertNotNull(navNoChildren.toSyntheticResource(context.resourceResolver(), "/test"));
    assertEquals("/synthetics/test/nav-no-children",
        navNoChildren.toSyntheticResource(context.resourceResolver(), "/test").getPath());
  }

  @Test
  public void testGetNavigationLinkElements() {
    assertEquals(3, navigation.getNavigationLinkElements().size());
  }

}
