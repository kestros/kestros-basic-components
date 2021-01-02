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

package io.kestros.cms.components.basic.structure.navigation;

import static org.junit.Assert.assertEquals;

import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NavigationTest {

  @Rule
  public SlingContext context = new SlingContext();

  private Navigation navigation;
  private Resource resource;
  private Map<String, Object> pageProperties = new HashMap<>();
  private Map<String, Object> jcrContentProperties = new HashMap<>();
  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    pageProperties.put("jcr:primaryType", "kes:Page");
    jcrContentProperties.put("jcr:primaryType", "nt:unstructured");
    jcrContentProperties.put("sling:resourceType", "page");
  }

  @Test
  public void testGetNavigationPages() throws NoValidAncestorException {
    resource = context.create().resource("/page", pageProperties);
    resource = context.create().resource("/page/child-1", pageProperties);
    resource = context.create().resource("/page/child-2", pageProperties);
    resource = context.create().resource("/page/child-3", pageProperties);
    resource = context.create().resource("/page/child-4", pageProperties);
    resource = context.create().resource("/page/jcr:content", jcrContentProperties);
    resource = context.create().resource("/page/jcr:content/navigation", properties);
    navigation = resource.adaptTo(Navigation.class);
    assertEquals("/page", navigation.getRootPage().getPath());
    assertEquals(4, navigation.getNavigationPages().size());
    assertEquals("/page/child-1", navigation.getNavigationPages().get(0).getPath());
    assertEquals("/page/child-2", navigation.getNavigationPages().get(1).getPath());
    assertEquals("/page/child-3", navigation.getNavigationPages().get(2).getPath());
    assertEquals("/page/child-4", navigation.getNavigationPages().get(3).getPath());
  }


  @Test
  public void testGetNavigationPagesWhenNavigationIsNested() throws NoValidAncestorException {
    resource = context.create().resource("/page", pageProperties);
    resource = context.create().resource("/page/child-1", pageProperties);
    resource = context.create().resource("/page/child-2", pageProperties);
    resource = context.create().resource("/page/child-2/grand-1", pageProperties);
    resource = context.create().resource("/page/child-3", pageProperties);
    resource = context.create().resource("/page/child-4", pageProperties);
    resource = context.create().resource("/page/jcr:content", jcrContentProperties);
    resource = context.create().resource("/page/child-2/jcr:content/navigation", properties);
    navigation = resource.adaptTo(Navigation.class);
    assertEquals("/page/child-2", navigation.getRootPage().getPath());
    assertEquals(1, navigation.getNavigationPages().size());
    assertEquals("/page/child-2/grand-1", navigation.getNavigationPages().get(0).getPath());
  }

  @Test
  public void testGetRootPage() throws NoValidAncestorException {
    properties.put("rootPage", "/page");
    resource = context.create().resource("/page", pageProperties);
    resource = context.create().resource("/page/jcr:content", jcrContentProperties);
    resource = context.create().resource("/page/jcr:content/navigation", properties);
    navigation = resource.adaptTo(Navigation.class);
    assertEquals("/page", navigation.getRootPage().getPath());
  }
}