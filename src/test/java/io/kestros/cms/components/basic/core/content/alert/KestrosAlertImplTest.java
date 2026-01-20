package io.kestros.cms.components.basic.core.content.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import java.util.ArrayList;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosAlertImplTest extends BaseSyntheticTest {
  private KestrosAlertImpl alert;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    alert = new KestrosAlertImpl("Heading", "Text", getResourceResolver(), getUiFramework(),
            "/parent", new ArrayList<>(),
            "default", "id", "forcedResourceName");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = alert.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/forcedResourceName", syntheticResource.getPath());
    assertEquals("Heading", syntheticResource.getValueMap().get("heading", String.class));
    assertEquals("Text", syntheticResource.getValueMap().get("text", String.class));
  }

  @Test
  public void testGetHeading() {
    assertEquals("Heading", alert.getHeading());
  }

  @Test
  public void testGetText() {
    assertEquals("Text", alert.getText());
  }
}