package io.kestros.cms.components.basic.core.content.video;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class VideoAssetDataSourceTest extends BaseDataSourceTest {

  private VideoAssetDataSource dataSource;

  private final Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/assets/collection");
  }

  /** Registers the asset retrieval service before adapting: the model reads it as an @OSGiService,
   * and without it every asset lookup returns null. */
  private void adaptWith(final Map<String, Object> componentProperties) {
    final Resource resource =
        context.create().resource("/content/page/jcr:content/video", componentProperties);
    context.request().setResource(resource);
    registerAssetRetrievalService();
    dataSource = context.request().adaptTo(VideoAssetDataSource.class);
  }

  private void adaptWithoutAssetService(final Map<String, Object> componentProperties) {
    final Resource resource =
        context.create().resource("/content/page/jcr:content/video-no-service",
            componentProperties);
    context.request().setResource(resource);
    dataSource = context.request().adaptTo(VideoAssetDataSource.class);
  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {
    adaptWith(properties);

    assertEquals(KestrosVideo.RESOURCE_TYPE,
        dataSource.toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content")
            .getResourceType());
  }

  @Test
  public void testGetVideoSource() {
    properties.put("videoPath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertEquals("/content/assets/collection/asset-1", dataSource.getVideoSource());
  }

  @Test
  public void testGetVideoSourceWhenNoVideoPathIsConfigured() {
    adaptWith(properties);

    assertNull(dataSource.getVideoSource());
  }

  @Test
  public void testGetVideoSourceWhenTheAssetDoesNotExist() {
    properties.put("videoPath", "/content/assets/collection/missing");
    adaptWith(properties);

    assertNull(dataSource.getVideoSource());
  }

  /** The resolved asset is cached, so a second call must not go back to the retrieval service. */
  @Test
  public void testGetAssetIsCachedAfterTheFirstLookup() {
    properties.put("videoPath", "/content/assets/collection/asset-1");
    adaptWith(properties);

    assertEquals(dataSource.getAsset(), dataSource.getAsset());
  }

  /** The service reference is @Optional, so an unbound service must yield null, not a failure. */
  @Test
  public void testGetVideoSourceWhenThereIsNoAssetRetrievalService() {
    properties.put("videoPath", "/content/assets/collection/asset-1");
    adaptWithoutAssetService(properties);

    assertNull(dataSource.getVideoSource());
  }

  @Test
  public void testGetFallbackText() {
    properties.put("fallbackText", "Your browser cannot play this video.");
    adaptWith(properties);

    assertEquals("Your browser cannot play this video.", dataSource.getFallbackText());
  }

  @Test
  public void testGetFallbackTextWhenNotConfigured() {
    adaptWith(properties);

    assertEquals("", dataSource.getFallbackText());
  }
}
