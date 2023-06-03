package io.kestros.cms.components.basic.content.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AlertComponentTest {

  @Rule
  public SlingContext context = new SlingContext();

  private AlertComponent alertComponent;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros.cms");
  }

  @Test
  public void getHeading() {
    properties.put("heading", "Test Heading");
    context.create().resource("/content/test", properties);
    resource = context.resourceResolver().getResource("/content/test");
    alertComponent = resource.adaptTo(AlertComponent.class);
    assertNotNull(alertComponent);
    assertEquals("Test Heading", alertComponent.getHeading());
  }

  @Test
  public void getIcon() {
    properties.put("icon", "Test Icon");
    context.create().resource("/content/test", properties);
    resource = context.resourceResolver().getResource("/content/test");
    alertComponent = resource.adaptTo(AlertComponent.class);
    assertNotNull(alertComponent);
    assertEquals("Test Icon", alertComponent.getIcon());
  }
}