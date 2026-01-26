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
    imageStaticDataSource = context.request().adaptTo(ImageStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {

  }

  @Test
  public void testGetImageTitle() {
    assertEquals("Asset 1 Title", imageStaticDataSource.getImageTitle());
  }

  @Test
  public void testGetImagePath() {
    assertEquals("/content/assets/collection/asset-1",
            imageStaticDataSource.getImagePath());
  }

  @Test
  public void testGetHref() {
    assertNull(imageStaticDataSource.getHref());
  }

  @Test
  public void testGetAriaLabel() {
    assertNull(imageStaticDataSource.getAriaLabel());
  }

  @Test
  public void testGetAnchorTitle() {
    assertNull(imageStaticDataSource.getAnchorTitle());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, imageStaticDataSource.getTarget());
  }

  @Test
  public void testGetAltText() {
    assertEquals("", imageStaticDataSource.getAltText());
  }

  @Test
  public void testGetCaption() {
    assertNull(imageStaticDataSource.getCaption());
  }

  @Test
  public void testGetAsset() {
    assertNotNull(imageStaticDataSource.getAsset());
  }

}