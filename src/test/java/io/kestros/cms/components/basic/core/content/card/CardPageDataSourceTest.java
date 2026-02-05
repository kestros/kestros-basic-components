package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardPageDataSourceTest extends BaseDataSourceTest {

  private CardPageDataSource cardPageDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    properties.put("pagePath", "/content/page");
    resource = context.create().resource("/content/page-card", properties);
    context.request().setResource(resource);

    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);
    setupSamplePage("/content/page", null);
  }

  @Override
  public void doComponentTypeSetup() {

  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(cardPageDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Title", cardPageDataSource.getTitle().getValueMap().get("text", String.class));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Description", cardPageDataSource.getDescription());
  }

  @Test
  public void testGetImageElement() throws AssetCollectionRetrievalException {
    properties.put("pagePath", "/content/page2");
    resource = context.create().resource("/content/page-card2", properties);
    context.request().setResource(resource);

    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);
    setupSamplePage("/content/page2", "/content/assets/collection/asset-1");
    setUpSampleCollection("/content/assets/collection");
    assertNotNull(cardPageDataSource.getImageElement());
  }

  @Test
  public void testGetImageElementWhenNoImage() {
    assertNull(cardPageDataSource.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    assertNotNull(cardPageDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetPage() {
    assertNotNull(cardPageDataSource.getPage());
  }
}