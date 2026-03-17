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
package io.kestros.cms.components.basic.core.content.heading;

import static org.junit.Assert.assertEquals;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class HeadingDataSourceComponentTest extends BaseDataSourceComponentTest {

  private HeadingDataSourceComponent heading;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosHeading.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosHeading.RESOURCE_TYPE);

  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {

  }

  @Test
  public void testGetHeadingText() {
    properties.put("kes:datasource", "default");
    properties.put("headingText", "Sample Heading");
    resource = context.create().resource("/content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("Sample Heading", heading.getHeadingText());
  }

  @Test
  public void testGetHeadingType() {
    properties.put("kes:datasource", "default");
    properties.put("headingType", "h1");
    resource = context.create().resource("/content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("h1", heading.getHeadingType());
  }


  @Test
  public void testGetHeadingTextWhenPageTitle() {
    Map<String,Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    Map<String,Object> pageJcrContentProperties = new HashMap<>();
    pageJcrContentProperties.put("jcr:title", "PageTitle");
    context.create().resource("/content/page", pageProperties);
    context.create().resource("/content/page/jcr:content", pageJcrContentProperties);

    properties.put("headingText", "Sample Heading");
    properties.put("kes:datasource", "page-title");
    resource = context.create().resource("/content/page/jcr:content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("PageTitle", heading.getHeadingText());
  }

  @Test
  public void testGetHeadingTypeWhenPageTitle() {
    properties.put("kes:datasource", "page-title");
    properties.put("headingType", "h1");
    resource = context.create().resource("/content/heading", properties);
    context.request().setResource(resource);
    heading = context.request().adaptTo(HeadingDataSourceComponent.class);
    assertEquals("h1", heading.getHeadingType());
  }

}