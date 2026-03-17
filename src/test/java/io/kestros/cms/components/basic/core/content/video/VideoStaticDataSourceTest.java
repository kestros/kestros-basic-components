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
package io.kestros.cms.components.basic.core.content.video;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class VideoStaticDataSourceTest extends BaseDataSourceTest {

  private VideoStaticDataSource videoStaticDataSource;
  private Map<String, Object> properties = new HashMap<>();
  private Resource resource;


  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {

  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    setUpSampleCollection("/content/assets/collection");
    properties.put("videoPath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/video", properties);
    context.request().setResource(resource);
    videoStaticDataSource = context.request().adaptTo(VideoStaticDataSource.class);
    assertEquals("/content/assets/collection/asset-1", videoStaticDataSource.getVideoSource());

    assertEquals(12, videoStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test/path").getValueMap().size());
    assertEquals("/content/assets/collection/asset-1", videoStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test/path").getValueMap().get("videoSource", String.class));
  }

  @Test
  public void testGetVideoSource() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    setUpSampleCollection("/content/assets/collection");
    properties.put("videoPath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/video", properties);
    context.request().setResource(resource);
    videoStaticDataSource = context.request().adaptTo(VideoStaticDataSource.class);
    assertEquals("/content/assets/collection/asset-1", videoStaticDataSource.getVideoSource());
  }

  @Test
  public void testGetVideoSourceWhenAssetDoesNotExist() throws AssetCollectionRetrievalException {
    registerAssetRetrievalService();
    properties.put("videoPath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/video", properties);
    context.request().setResource(resource);
    videoStaticDataSource = context.request().adaptTo(VideoStaticDataSource.class);
    assertNull(videoStaticDataSource.getVideoSource());
  }
}