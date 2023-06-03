package io.kestros.cms.components.basic.content.buttongroup;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ButtonGroupComponentTest {

  @Rule
  public SlingContext context = new SlingContext();

  private ButtonGroupComponent buttonGroupComponent;

  private Resource resource;

  private Map<String,Object> properties = new HashMap<>();
  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros.cms");
  }

  @Test
  public void testGetButtons() {
    resource = context.create().resource("/content/test");
    context.create().resource("/content/test/buttons/button1");
    context.create().resource("/content/test/buttons/button2");
    context.create().resource("/content/test/buttons/button3");

    buttonGroupComponent = resource.adaptTo(ButtonGroupComponent.class);
    assertEquals(3, buttonGroupComponent.getButtons().size());
  }
}