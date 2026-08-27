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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardStaticDataSourceTest extends BaseDataSourceTest {
  private CardStaticDataSource cardStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosCard.RESOURCE_TYPE, properties);
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("title", "Test Title");
    properties.put("description", "Test Description");
    properties.put("id", "id");
    properties.put("variations", new String[]{"variation1", "variation2", "variation3"});

    resource = context.create().resource("/content/card/static/card", properties);
    context.request().setResource(resource);

  }

  @Override
  public void testToSyntheticResource() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetTitle() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    properties.put("headingText", "Test Title");
    properties.put("headingType", "h3");
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);

    assertEquals("Test Title",
            cardStaticDataSource.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetDescription() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals("Test Description", cardStaticDataSource.getDescription());
  }

  @Test
  public void testGetImageElement() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    setUpSampleCollection("/content/assets/collection");
    properties.put("imagePath", "/content/assets/collection/asset-1");
    properties.put("imageVariations", new String[]{"variation1", "variation2", "variation3"});
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getImageElement());
    assertNotNull(cardStaticDataSource.getImage());
    assertEquals(3, cardStaticDataSource.getImageElement().getVariations().size());
  }

  @Test
  public void testGetImageElementWhenDoesNotExist() throws AssetCollectionRetrievalException {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    properties.put("imagePath", "/content/assets/collection/asset-1");
    properties.put("imageVariations", new String[]{"variation1", "variation2", "variation3"});
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getImageElement());
    assertNotNull(cardStaticDataSource.getImage());
  }

  @Test
  public void testGetImageElementWhenNoImage() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNull(cardStaticDataSource.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    properties.put("href", "/content/dam/image.jpg");
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(1, cardStaticDataSource.getChildren().size());
    assertNotNull(cardStaticDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetButtonGroupElementWhenNoButtons() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNull(cardStaticDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetChildren() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    setUpSampleCollection("/content/assets/collection");
    properties.put("href", "/content/dam/image.jpg");
    properties.put("imagePath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(2, cardStaticDataSource.getChildren().size());
  }

  @Test
  public void testGetChildrenWhenNoChildren() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(0, cardStaticDataSource.getChildren().size());
  }

  @Test
  public void testGetId() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals("id", cardStaticDataSource.getId());
  }

  @Test
  public void testIsSynthetic() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertFalse(cardStaticDataSource.isSynthetic());
  }

  @Test
  public void testGetResource() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getResource());
    assertEquals("/content/card/static/card", cardStaticDataSource.getResource().getPath());
  }

  @Test
  public void testGetResourceResolver() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getResourceResolver());
  }

  @Test
  public void testGetParentPath() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals("/content/card/static", cardStaticDataSource.getParentPath());
  }

  @Test
  public void testGetVariations() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(3, cardStaticDataSource.getVariations().size());
  }

  @Test
  public void testGetLayout() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getLayout());
    assertEquals("default", cardStaticDataSource.getLayout());
  }

  @Test
  public void testGetForcedResourceName() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals("card", cardStaticDataSource.getForcedResourceName());
  }

  @Test
  public void testGetUiFramework() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getUiFramework());
  }

  @Test
  public void testTestGetLayout() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getLayout());
  }

  @Test
  public void testGetAppliedVariations() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(3, cardStaticDataSource.getVariations().size());
  }

  @Test
  public void testGetInlineVariations() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals("", cardStaticDataSource.getInlineVariations());
  }

  @Test
  public void testGetPath() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals("/content/card/static/card", cardStaticDataSource.getPath());
  }


  @Test
  public void testIsDataSourceComponent() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertTrue(cardStaticDataSource.isDataSourceComponent());
  }

  @Test
  public void testGetRequestAttributes() {
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getRequestAttributes());
  }
}
