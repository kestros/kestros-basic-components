package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardStaticDataSourceTest extends BaseDataSourceTest {
  private CardStaticDataSource cardStaticDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosCard.RESOURCE_TYPE, properties);
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("title", "Test Title");
    properties.put("description", "Test Description");
    properties.put("id", "id");
    properties.put("variations", new String[]{"variation1", "variation2", "variation3"});

    resource = context.create().resource("/content/card/static/card", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(cardStaticDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetTitle() {
    properties.put("text", "Test Title");
    properties.put("headingLevel", "h3");
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);

    assertEquals("Test Title", cardStaticDataSource.getTitle().getValueMap().get("text", String.class));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Test Description", cardStaticDataSource.getDescription());
  }

  @Test
  public void testGetImageElement() {
    properties.put("imagePath", "/content/dam/image.jpg");
    properties.put("imageVariations", new String[]{"variation1", "variation2", "variation3"});
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource.getImageElement());
    assertNotNull(cardStaticDataSource.getImage());
    assertEquals(3, cardStaticDataSource.getImageElement().getVariations().size());
  }

  @Test
  public void testGetImageElementWhenNoImage() {
    assertNull(cardStaticDataSource.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    properties.put("href", "/content/dam/image.jpg");
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(1, cardStaticDataSource.getChildren().size());
    assertNotNull(cardStaticDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetButtonGroupElementWhenNoButtons() {
    assertNull(cardStaticDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetChildren() {
    properties.put("href", "/content/dam/image.jpg");
    properties.put("imagePath", "/content/dam/image.jpg");
    resource = context.create().resource("/content/card/static/card2", properties);
    context.request().setResource(resource);
    cardStaticDataSource = context.request().adaptTo(CardStaticDataSource.class);
    assertEquals(2, cardStaticDataSource.getChildren().size());
  }

  @Test
  public void testGetChildrenWhenNoChildren() {
    assertEquals(0, cardStaticDataSource.getChildren().size());
  }

  @Test
  public void testGetId() {
    assertEquals("id", cardStaticDataSource.getId());
  }

  @Test
  public void testIsSynthetic() {
    assertFalse(cardStaticDataSource.isSynthetic());
  }

  @Test
  public void testGetResource() {
    assertNotNull(cardStaticDataSource.getResource());
    assertEquals("/content/card/static/card", cardStaticDataSource.getResource().getPath());
  }

  @Test
  public void testGetResourceResolver() {
    assertNotNull(cardStaticDataSource.getResourceResolver());
  }

  @Test
  public void testGetParentPath() {
    assertEquals("/content/card/static", cardStaticDataSource.getParentPath());
  }

  @Test
  public void testGetVariations() {
    assertEquals(3, cardStaticDataSource.getVariations().size());
  }

  @Test
  public void testGetLayout() {
    assertNotNull(cardStaticDataSource.getLayout());
    assertEquals("default", cardStaticDataSource.getLayout());
  }

  @Test
  public void testGetForcedResourceName() {
    assertEquals("card", cardStaticDataSource.getForcedResourceName());
  }

  @Test
  public void testGetUiFramework() {
    assertNotNull(cardStaticDataSource.getUiFramework());
  }

  @Test
  public void testTestGetLayout() {
    assertNotNull(cardStaticDataSource.getLayout());
  }

  @Test
  public void testGetAppliedVariations() {
    assertEquals(3, cardStaticDataSource.getVariations().size());
  }

  @Test
  public void testGetInlineVariations() {
    assertEquals("", cardStaticDataSource.getInlineVariations());
  }

  @Test
  public void testGetPath() {
    assertEquals("/content/card/static/card", cardStaticDataSource.getPath());
  }


  @Test
  public void testIsDataSourceComponent() {
    assertTrue(cardStaticDataSource.isDataSourceComponent());
  }

  @Test
  public void testGetRequestAttributes() {
    assertNotNull(cardStaticDataSource.getRequestAttributes());
  }
}