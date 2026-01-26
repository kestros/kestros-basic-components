package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardDataSourceComponentTest extends BaseDataSourceComponentTest {

  private CardDataSourceComponent cardDataSourceComponent;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    setupSamplePage("/content/page", null);
    properties.put("title", "Test Title");
    properties.put("description", "Test Description");
    properties.put("sling:resourceType", KestrosCard.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    resource = context.create().resource("/content/page/jcr:content/static/card", properties);
    context.request().setResource(resource);
    cardDataSourceComponent = context.request().adaptTo(CardDataSourceComponent.class);
  }

  @Override
  public Map<String, String> getDataSourceMap() {
    Map<String, String> dataSourceMap = new HashMap<>();
    dataSourceMap.put("default",
            "io.kestros.cms.components.basic.core.content.card.CardStaticDataSource");
    return dataSourceMap;
  }

  @Override
  public String getResourceType() {
    return KestrosCard.RESOURCE_TYPE;
  }


  @Override
  public void testToSyntheticResource() {
    assertNotNull(
            cardDataSourceComponent.toSyntheticResource(context.resourceResolver(), "/test/path"));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Test Title", cardDataSourceComponent.getTitle());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Test Description", cardDataSourceComponent.getDescription());
  }

  @Test
  public void testGetImageElementWhenNoImage() {
    assertNull(cardDataSourceComponent.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    assertNull(cardDataSourceComponent.getButtonGroupElement());
  }
}