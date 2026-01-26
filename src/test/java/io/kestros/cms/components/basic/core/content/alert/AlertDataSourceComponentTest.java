package io.kestros.cms.components.basic.core.content.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.core.BaseDataSourceComponentTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class AlertDataSourceComponentTest extends BaseDataSourceComponentTest {
  private AlertDataSourceComponent alert;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public Map<String, String> getDataSourceMap() {
    Map<String, String> dataSourceMap = new HashMap<>();
    dataSourceMap.put("default",
            "io.kestros.cms.components.basic.core.content.alert.AlertStaticDataSource");
    return dataSourceMap;
  }

  @Override
  public String getResourceType() {
    return KestrosAlert.RESOURCE_TYPE;
  }

  @Override
  public void doComponentSetup() {
    properties.put("heading", "Test Heading");
    properties.put("text", "Test Text");
    properties.put("sling:resourceType", KestrosAlert.RESOURCE_TYPE);
    properties.put("kes:datasource", "default");
    resource = context.create().resource("/content/alert/static/heading", properties);

    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertDataSourceComponent.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(alert.toSyntheticResource(context.resourceResolver(), "/test/path"));
  }

  @Test
  public void testGetHeading() {
    String heading = alert.getHeading();
    assertEquals("Test Heading", heading);
  }

  @Test
  public void testGetText() {
    String text = alert.getText();
    assertEquals("Test Text", text);
  }


}