package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardListAssetsDataSourceTest extends BaseDataSourceTest {

  private CardListAssetsDataSource cardListAssetsDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/page", null);
    setUpSampleCollection("/content/assets/collection");

    properties.put("collectionPath", "/content/assets/collection");
    resource = context.create().resource("/content/page/cardlist/assets", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
  }

  @Override
  public void doComponentTypeSetup() {

  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(
            cardListAssetsDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetCollection() {
    assertNotNull(cardListAssetsDataSource.getCollection());
  }

  @Test
  public void testGetCards() {
    assertEquals(3, cardListAssetsDataSource.getCards().size());
  }
}