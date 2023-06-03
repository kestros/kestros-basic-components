package io.kestros.cms.components.basic.content.button;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ButtonComponentTest {

  @Rule
  public SlingContext context = new SlingContext();

  private ButtonComponent buttonComponent;

  private Resource resource;

  private Map<String,Object> properties = new HashMap<>();
  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros.cms");
  }

  @Test
  public void testGetLink() {
    properties.put("link", "https://kestros.io");
    resource = context.create().resource("/content/test", properties);
    buttonComponent = resource.adaptTo(ButtonComponent.class);
    assertEquals("https://kestros.io", buttonComponent.getLink());
  }

  @Test
  public void testGetLinkWhenPath() {
    properties.put("link", "/content/test");
    resource = context.create().resource("/content/test", properties);
    buttonComponent = resource.adaptTo(ButtonComponent.class);
    assertEquals("/content/test.html", buttonComponent.getLink());
  }
}