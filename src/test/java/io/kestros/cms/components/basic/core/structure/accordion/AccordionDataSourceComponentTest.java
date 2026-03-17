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
package io.kestros.cms.components.basic.core.structure.accordion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class AccordionDataSourceComponentTest extends BaseDataSourceComponentTest {
  private AccordionDataSourceComponent accordion;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosAccordion.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosAccordion.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");

    resource = context.create().resource("/content/sites/page/child-3/jcr:content/accordion",
        properties);
    context.create().resource("/content/sites/page/child-3/jcr:content/accordion/panel-1",
        properties);
    context.create().resource("/content/sites/page/child-3/jcr:content/accordion/panel-2",
        properties);
    context.create().resource("/content/sites/page/child-3/jcr:content/accordion/panel-3",
        properties);
    context.request().setResource(resource);

    accordion = context.request().adaptTo(AccordionDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(accordion.toSyntheticResource(context.resourceResolver(),
        "/synthetics/content/sites/page/child-3/jcr:content"));
  }

  @Test
  public void testGetPanelElements() {
    assertEquals(3, accordion.getPanelElements().size());
  }

}