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

  /**
   * With no AssetRetrievalService bound, getVideoAsset() returns null. getVideoSource() used to
   * dereference it, so a video component on an instance without the asset service threw instead
   * of falling back to its fallback text.
   */
  @Test
  public void testGetVideoSourceWhenThereIsNoAssetRetrievalService() {
    properties.put("videoPath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/video/no-service", properties);
    context.request().setResource(resource);
    videoStaticDataSource = context.request().adaptTo(VideoStaticDataSource.class);

    assertNull(videoStaticDataSource.getVideoSource());
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