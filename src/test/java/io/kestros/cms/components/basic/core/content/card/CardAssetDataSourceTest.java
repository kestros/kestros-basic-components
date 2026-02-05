package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardAssetDataSourceTest extends BaseDataSourceTest {

  private CardAssetDataSource cardAssetDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/page", null);
    setUpSampleCollection("/content/assets/collection");

    properties.put("imagePath", "/content/assets/collection/asset-1");
    properties.put("sling:resourceType", KestrosCard.RESOURCE_TYPE);
    resource = context.create().resource("/content/page/card/assets", properties);
    context.request().setResource(resource);
    cardAssetDataSource = context.request().adaptTo(CardAssetDataSource.class);
  }


  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosCard.RESOURCE_TYPE, properties);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(cardAssetDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Asset 1 Title", cardAssetDataSource.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Asset 1 Description", cardAssetDataSource.getDescription());
  }

  @Test
  public void testGetImageElement() {
    assertNotNull(cardAssetDataSource.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    assertNull(cardAssetDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetAsset() {
    assertNotNull(cardAssetDataSource.getAsset());
  }

}