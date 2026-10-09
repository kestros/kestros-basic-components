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

package io.kestros.cms.components.basic.core.content.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class ImageAssetDataSourceTest extends BaseDataSourceTest {

  private ImageAssetDataSource dataSource;

  private final Map<String, Object> properties = new HashMap<>();

  private int resourceCounter;

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/assets/collection");
  }

  /**
   * Registers the asset retrieval service before adapting: the model reads it as an
   * {@code @OSGiService}, and without it every asset lookup returns null.
   */
  private void adaptWith(final Map<String, Object> componentProperties) {
    resourceCounter++;
    final Resource resource = context.create().resource(
        "/content/page/jcr:content/image-" + resourceCounter, componentProperties);
    context.request().setResource(resource);
    registerAssetRetrievalService();
    dataSource = context.request().adaptTo(ImageAssetDataSource.class);
  }

  private void adaptWithoutAssetService(final Map<String, Object> componentProperties) {
    resourceCounter++;
    final Resource resource = context.create().resource(
        "/content/page/jcr:content/no-service-" + resourceCounter, componentProperties);
    context.request().setResource(resource);
    dataSource = context.request().adaptTo(ImageAssetDataSource.class);
  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {
    adaptWith(properties);

    assertNotNull(
        dataSource.toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content"));
  }

  @Test
  public void testGetImagePath() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertEquals("/content/assets/collection/asset-1", dataSource.getImagePath());
  }

  @Test
  public void testGetImagePathWhenBlank() {
    properties.put("imagePath", "   ");
    adaptWith(properties);

    assertNull(dataSource.getImagePath());
  }

  @Test
  public void testGetImagePathWhenNotConfigured() {
    adaptWith(properties);

    assertNull(dataSource.getImagePath());
  }

  @Test
  public void testGetImageTitleComesFromTheAsset() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertEquals("Asset 1 Title", dataSource.getImageTitle());
  }

  /** With no asset behind the path, the component's own imageTitle property is the fallback. */
  @Test
  public void testGetImageTitleFallsBackToTheComponentProperty() {
    properties.put("imagePath", "/content/assets/collection/missing");
    properties.put("imageTitle", "Configured Title");
    adaptWith(properties);

    assertEquals("Configured Title", dataSource.getImageTitle());
  }

  @Test
  public void testGetImageTitleWhenThereIsNeither() {
    adaptWith(properties);

    assertEquals("", dataSource.getImageTitle());
  }

  /** An explicit altText wins over the asset's description. */
  @Test
  public void testGetAltTextPrefersTheConfiguredValue() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    properties.put("altText", "Configured Alt");
    adaptWith(properties);

    assertEquals("Configured Alt", dataSource.getAltText());
  }

  @Test
  public void testGetAltTextFallsBackToTheAssetDescription() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertEquals("Asset 1 Description", dataSource.getAltText());
  }

  @Test
  public void testGetAltTextWhenThereIsNeither() {
    adaptWith(properties);

    assertEquals("", dataSource.getAltText());
  }

  @Test
  public void testGetAltTextWhenTheConfiguredValueIsBlank() {
    properties.put("altText", "   ");
    adaptWith(properties);

    assertEquals("", dataSource.getAltText());
  }

  @Test
  public void testGetCaption() {
    properties.put("caption", "A caption");
    adaptWith(properties);

    assertEquals("A caption", dataSource.getCaption());
  }

  @Test
  public void testGetCaptionWhenNotConfigured() {
    adaptWith(properties);

    assertNull(dataSource.getCaption());
  }

  @Test
  public void testGetAriaLabel() {
    properties.put("ariaLabel", "Product photo");
    adaptWith(properties);

    assertEquals("Product photo", dataSource.getAriaLabel());
  }

  @Test
  public void testGetAriaLabelWhenNotConfigured() {
    adaptWith(properties);

    assertNull(dataSource.getAriaLabel());
  }

  @Test
  public void testTheLinkGettersAreAlwaysEmpty() {
    adaptWith(properties);

    assertNull(dataSource.getHref());
    assertNull(dataSource.getAnchorTitle());
    assertNull(dataSource.getRel());
    assertNull(dataSource.getAriaDescribedBy());
    assertNull(dataSource.getLang());
    assertEquals(AnchorTarget.SAME_WINDOW, dataSource.getTarget());
  }

  @Test
  public void testGetAsset() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertNotNull(dataSource.getAsset());
  }

  /** The resolved asset is cached, so a second call returns the same instance. */
  @Test
  public void testGetAssetIsCachedAfterTheFirstLookup() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertEquals(dataSource.getAsset(), dataSource.getAsset());
  }

  @Test
  public void testGetAssetWhenThePathDoesNotResolve() {
    properties.put("imagePath", "/content/assets/collection/missing");
    adaptWith(properties);

    assertNull(dataSource.getAsset());
  }

  @Test
  public void testGetAssetWhenNoPathIsConfigured() {
    adaptWith(properties);

    assertNull(dataSource.getAsset());
  }

  /** The service reference is @Optional, so an unbound service must yield null, not a failure. */
  @Test
  public void testGetAssetWhenThereIsNoAssetRetrievalService() {
    properties.put("imagePath", "/content/assets/collection/asset-1");
    adaptWithoutAssetService(properties);

    assertNull(dataSource.getAsset());
  }
}
