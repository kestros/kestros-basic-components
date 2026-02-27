package io.kestros.cms.components.basic.core.content.button;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class ButtonDataSourceComponentTest extends BaseDataSourceComponentTest {

  private ButtonDataSourceComponent button;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public String getResourceType() {
    return KestrosButton.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    properties.put("sling:resourceType", KestrosButton.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    properties.put("text", "Click Me");
    properties.put("href", "/path/to/page");
    properties.put("title", "Button Title");

    resource = context.create().resource("/content/button", properties);
    context.request().setResource(resource);

    button = context.request().adaptTo(ButtonDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(button.toSyntheticResource(context.resourceResolver(),
        "/synthetics/content"));
  }

  @Test
  public void testGetText() {
    assertEquals("Click Me", button.getText());
  }

  @Test
  public void testGetHref() {
    assertEquals("/path/to/page.html", button.getHref());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Button Title", button.getTitle());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, button.getTarget());
  }

  @Test
  public void testGetRel() {
    assertNull(button.getRel());
  }

  @Test
  public void testGetAriaLabel() {
    assertNull(button.getAriaLabel());
  }

  @Test
  public void testGetAriaDescribedBy() {
    assertNull(button.getAriaDescribedBy());
  }

  @Test
  public void testGetLang() {
    assertNull(button.getLang());
  }

  @Test
  public void testIsDisabled() {
    assertFalse(button.isDisabled());
  }

  @Test
  public void testGetComponentResourceType() {
    assertEquals(KestrosButton.RESOURCE_TYPE, button.getComponentResourceType());
  }

  @Test
  public void testGetResource() {
    assertNotNull(button.getResource());
  }

  @Test
  public void testGetPath() {
    assertNotNull(button.getPath());
  }

  @Test
  public void testIsDisabledWhenTrue() {
    properties.clear();
    properties.put("sling:resourceType", KestrosButton.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    properties.put("disabled", true);
    resource = context.create().resource("/content/button-2", properties);
    context.request().setResource(resource);
    button = context.request().adaptTo(ButtonDataSourceComponent.class);

    assertTrue(button.isDisabled());
  }

}
