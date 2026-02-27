package io.kestros.cms.components.basic.core.content.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class ImageDataSourceComponentTest extends BaseDataSourceComponentTest {

  private ImageDataSourceComponent image;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosImage.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/assets/collection");
    properties.put("sling:resourceType", KestrosImage.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    properties.put("imagePath", "/content/assets/collection/asset-1");
    properties.put("altText", "Alt text");

    resource = context.create().resource("/content/image", properties);
    context.request().setResource(resource);

    image = context.request().adaptTo(ImageDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    registerAssetRetrievalService();
    assertNotNull(image.toSyntheticResource(context.resourceResolver(),
        "/synthetics/content"));
  }

  @Test
  public void testGetImagePath() {
    assertEquals("/content/assets/collection/asset-1", image.getImagePath());
  }

  @Test
  public void testGetAltText() {
    assertEquals("Alt text", image.getAltText());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, image.getTarget());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosImage.RESOURCE_TYPE, image.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(image.getResource());
  }

  @Test
  public void testGetPath() {
    assertNotNull(image.getPath());
  }

}
