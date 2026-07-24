package io.kestros.cms.components.basic.core.content.video;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class VideoDataSourceComponentTest extends BaseDataSourceComponentTest {

  private VideoDataSourceComponent video;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosVideo.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/assets/collection");

    properties.put("sling:resourceType", KestrosVideo.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    properties.put("fallbackText", "Fallback Text");
    properties.put("videoPath", "/content/assets/collection/asset-1");
    resource = context.create().resource("/content/video", properties);

    context.request().setResource(resource);
    video = context.request().adaptTo(VideoDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    registerAssetRetrievalService();
    assertNotNull(video.toSyntheticResource(context.resourceResolver(), "/test/path"));
    assertEquals("/synthetics/test/path/video", video.toSyntheticResource(context.resourceResolver(), "/test/path").getPath());
  }

  @Test
  public void testGetVideoSource() {
    registerAssetRetrievalService();
    assertEquals("/content/assets/collection/asset-1", video.getVideoSource());
  }

  @Test
  public void testGetFallbackText() {
    assertEquals("Fallback Text", video.getFallbackText());
  }

}