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

public class ImageStaticDataSourceTest extends BaseDataSourceTest {
  private ImageStaticDataSource imageStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/assets/collection");
    properties.put("imagePath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/image/static/image", properties);
    context.request().setResource(resource);

  }

  @Override
  public void testToSyntheticResource() {
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
  }

  @Test
  public void testGetImageTitle() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertEquals("Asset 1 Title", imageStaticDataSource.getImageTitle());
  }

  @Test
  public void testGetImagePath() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertEquals("/content/assets/collection/asset-1",
            imageStaticDataSource.getImagePath());
  }

  @Test
  public void testGetHref() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertNull(imageStaticDataSource.getHref());
  }

  @Test
  public void testGetAriaLabel() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertNull(imageStaticDataSource.getAriaLabel());
  }

  @Test
  public void testGetAnchorTitle() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertNull(imageStaticDataSource.getAnchorTitle());
  }

  @Test
  public void testGetTarget() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertEquals(AnchorTarget.SAME_WINDOW, imageStaticDataSource.getTarget());
  }

  @Test
  public void testGetAltText() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertEquals("", imageStaticDataSource.getAltText());
  }

  @Test
  public void testGetCaption() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertNull(imageStaticDataSource.getCaption());
  }

  @Test
  public void testGetAsset() {
    registerAssetRetrievalService();
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
    assertNotNull(imageStaticDataSource.getAsset());
  }

  /** altText wins over the asset description; with neither, the result is the empty string. */
  @Test
  public void testGetAltTextPrefersTheConfiguredValue() {
    registerAssetRetrievalService();
    properties.put("altText", "Configured Alt");
    resource = context.create().resource("/content/image/static/alt-configured", properties);
    context.request().setResource(resource);

    assertEquals("Configured Alt",
        context.request().adaptTo(ImageStaticDataSource.class).getAltText());
  }

  @Test
  public void testGetAltTextWhenTheConfiguredValueIsBlank() {
    registerAssetRetrievalService();
    final Map<String, Object> props = new HashMap<>();
    props.put("altText", "   ");
    resource = context.create().resource("/content/image/static/alt-blank", props);
    context.request().setResource(resource);

    assertEquals("", context.request().adaptTo(ImageStaticDataSource.class).getAltText());
  }

  @Test
  public void testGetAltTextWhenThereIsNoImagePath() {
    registerAssetRetrievalService();
    final Map<String, Object> props = new HashMap<>();
    resource = context.create().resource("/content/image/static/alt-none", props);
    context.request().setResource(resource);

    assertEquals("", context.request().adaptTo(ImageStaticDataSource.class).getAltText());
  }

  @Test
  public void testGetAssetWhenThePathDoesNotResolve() {
    registerAssetRetrievalService();
    final Map<String, Object> props = new HashMap<>();
    props.put("imagePath", "/content/assets/collection/missing");
    resource = context.create().resource("/content/image/static/missing-asset", props);
    context.request().setResource(resource);

    assertNull(context.request().adaptTo(ImageStaticDataSource.class).getAsset());
  }

  @Test
  public void testGetImageTitleFallsBackToTheComponentProperty() {
    registerAssetRetrievalService();
    final Map<String, Object> props = new HashMap<>();
    props.put("imageTitle", "Configured Title");
    resource = context.create().resource("/content/image/static/title-configured", props);
    context.request().setResource(resource);

    assertEquals("Configured Title",
        context.request().adaptTo(ImageStaticDataSource.class).getImageTitle());
  }
}
