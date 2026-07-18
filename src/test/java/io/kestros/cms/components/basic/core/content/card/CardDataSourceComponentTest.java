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

package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardDataSourceComponentTest extends BaseDataSourceComponentTest {

  private CardDataSourceComponent cardDataSourceComponent;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/page", null);
    setUpSampleCollection("/content/assets/collection");
    properties.put("description", "Test Description");
    properties.put("sling:resourceType", KestrosCard.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    resource = context.create().resource("/content/page/jcr:content/static/card", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);
  }

  @Override
  public String getResourceType() {
    return KestrosCard.RESOURCE_TYPE;
  }


  @Override
  public void testToSyntheticResource() {
    registerAssetRetrievalService();
    properties.put("imagePath", "/content/assets/collection/asset-1");
    properties.put("href", "/some/path");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    Resource synthetic = cardDataSourceComponent.toSyntheticResource(context.resourceResolver(),
        "/test/path");
    context.request().setResource(synthetic);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertNotNull(synthetic);
    assertNotNull(cardDataSourceComponent);
    assertEquals(2,
        SlingModelUtils.getChildrenAsBaseResource(cardDataSourceComponent.getResource()).size());

    Resource buttonGroupResource = cardDataSourceComponent.getButtonGroup();
    assertNotNull(buttonGroupResource);
    assertEquals("/synthetics/test/path/card-2/buttonGroupElement", buttonGroupResource.getPath());
    assertEquals(1, SlingModelUtils.getChildrenAsBaseResource(buttonGroupResource).size());
    assertEquals(13, buttonGroupResource.getValueMap().size());

    Resource buttonResource = SlingModelUtils.getChildrenAsBaseResource(
        cardDataSourceComponent.getButtonGroup()).get(0).getResource();
    assertNotNull(buttonResource);
    assertEquals("/synthetics/content/page/jcr:content/static/card-2/buttonElement", buttonResource.getPath());
    assertEquals(0,
        SlingModelUtils.getChildrenAsBaseResource(buttonResource).size());
    assertEquals(20, buttonResource.getValueMap().size());

    Resource imageResource = cardDataSourceComponent.getImage();
    assertNotNull(imageResource);
    assertEquals("/synthetics/test/path/card-2/imageElement", imageResource.getPath());
    assertEquals(0, SlingModelUtils.getChildrenAsBaseResource(imageResource).size());
    assertEquals(22, imageResource.getValueMap().size());
  }

  @Test
  public void testGetTitle() {
    properties.put("headingText", "Test Title");
    properties.put("headingType", "h3");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertEquals("Test Title", cardDataSourceComponent.getTitle().getValueMap().get("headingText", String.class));
    assertEquals("h3", cardDataSourceComponent.getTitle().getValueMap().get("headingType", String.class));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Test Description", cardDataSourceComponent.getDescription());
  }

  @Test
  public void testGetImageElement() {
    registerAssetRetrievalService();
    properties.put("imagePath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertNotNull(cardDataSourceComponent.getImageElement());
  }

  @Test
  public void testGetImageElementWhenNoImage() {
    assertNull(cardDataSourceComponent.getImageElement());
    assertNull(cardDataSourceComponent.getImage());
  }

  @Test
  public void testGetImage() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    properties.put("imagePath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertNotNull(cardDataSourceComponent.getImage());
  }

  @Test
  public void testGetImageWhenDoesNotExist() {
    registerAssetRetrievalService();
    properties.put("imagePath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertNotNull(cardDataSourceComponent.getImage());
  }

  @Test
  public void testGetButtonGroupElement() {
    properties.put("href", "/some/path");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertNotNull(cardDataSourceComponent.getButtonGroupElement());
  }

  @Test
  public void testGetButtonGroupElementWhenNoButtonGroup() {
    assertNull(cardDataSourceComponent.getButtonGroupElement());
    assertNull(cardDataSourceComponent.getButtonGroup());
  }

  @Test
  public void testGetButtonGroup() {
    properties.put("href", "/some/path");
    resource = context.create().resource("/content/page/jcr:content/static/card-2", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);

    assertNotNull(cardDataSourceComponent.getButtonGroup());
    assertNotNull(cardDataSourceComponent.getButtonGroup());
  }


}