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

package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class BreadCrumbsDataSourceComponentTest extends BaseDataSourceComponentTest {
  private BreadCrumbsDataSourceComponent breadCrumbs;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosBreadCrumbs.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/sites/page", null);
    properties.put("sling:resourceType", KestrosBreadCrumbs.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/breadcrumbs",
        properties);
    context.request().setResource(resource);

    breadCrumbs = context.request().adaptTo(BreadCrumbsDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(breadCrumbs.toSyntheticResource(context.resourceResolver(), "/synthetics/content/sites/page/child-3/jcr:content"));

  }

  @Test
  public void testGetLinkElements() {
    context.request().setPathInfo("/content/sites/page/child-3.html");
    assertEquals(2, breadCrumbs.getLinkElements().size());
    assertEquals("/synthetics/content/sites/page/child-3/jcr:content/breadcrumbs/page", breadCrumbs.getLinkElements().get(0).getPath());
    assertEquals("_self", breadCrumbs.getLinkElements().get(0).getTargetAsString());
    assertEquals("/content/sites/page.html", breadCrumbs.getLinkElements().get(0).getHref());
    assertEquals(null, breadCrumbs.getLinkElements().get(0).getLang());
    assertEquals(null, breadCrumbs.getLinkElements().get(0).getRel());
    assertEquals(null, breadCrumbs.getLinkElements().get(0).getTitle());
    assertEquals("Title", breadCrumbs.getLinkElements().get(0).getText());
    assertEquals(null, breadCrumbs.getLinkElements().get(0).getAriaLabel());
    assertEquals(null, breadCrumbs.getLinkElements().get(0).getAriaDescribedBy());
    
    Resource link0Resource = breadCrumbs.getLinkElements().get(0).toSyntheticResource(
        context.resourceResolver(), "/synthetics/content/sites/page/child-3/jcr:content/breadcrumbs/page");
    assertNotNull(link0Resource);
    Resource link1Resource = breadCrumbs.getLinkElements().get(1).toSyntheticResource(
        context.resourceResolver(), "/synthetics/content/sites/page/child-3/jcr:content/breadcrumbs/page");
    assertNotNull(link1Resource);

    context.request().setResource(link0Resource);
    KestrosBreadCrumb crumb0 = link0Resource.adaptTo(BreadCrumbDataSourceComponent.class);

    assertEquals("/synthetics/content/sites/page/child-3/jcr:content/breadcrumbs/page/page", crumb0.getPath());
    assertTrue(crumb0.isFirstItem());
    assertFalse(crumb0.isLastItem());
    assertEquals("_self", crumb0.getTargetAsString());
    assertEquals("/content/sites/page.html", crumb0.getHref());
    assertEquals(null, crumb0.getLang());
    assertEquals(null, crumb0.getRel());
    assertEquals(null, crumb0.getTitle());
    assertEquals("Title", crumb0.getText());
    assertEquals(null, crumb0.getAriaLabel());
    assertEquals(null, crumb0.getAriaDescribedBy());

    context.request().setResource(link1Resource);
    KestrosBreadCrumb crumb1 = link1Resource.adaptTo(BreadCrumbDataSourceComponent.class);

    assertEquals("/synthetics/content/sites/page/child-3/jcr:content/breadcrumbs/page/child-3", crumb1.getPath());
    assertFalse(crumb1.isFirstItem());
    assertTrue(crumb1.isLastItem());
    assertEquals("_self", crumb1.getTargetAsString());
    assertEquals("/content/sites/page/child-3.html", crumb1.getHref());
    assertEquals(null, crumb1.getLang());
    assertEquals(null, crumb1.getRel());
    assertEquals(null, crumb1.getTitle());
    assertEquals("Title", crumb1.getText());
    assertEquals(null, crumb1.getAriaLabel());
    assertEquals(null, crumb1.getAriaDescribedBy());

  }
}