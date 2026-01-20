package io.kestros.cms.components.basic.api;

import static org.junit.Assert.*;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.alert.KestrosAlertImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class KestrosBasicComponentElementTest extends BaseSyntheticTest {
  private KestrosAlertImpl alert;
  @Override
  public void setupElement() throws ComponentConfigurationException {
    List<ComponentVariation> variationList = new ArrayList<>();

    alert = new KestrosAlertImpl("Heading", "Text", getResourceResolver(), getUiFramework(),
            "/parent", variationList,
            "default", "id", "forcedResourceName");
  }
  @Test
  public void testGetLayout() {
    assertEquals("default", alert.getLayout());
  }

  @Test
  public void testGetAppliedVariations() {

  }

  @Test
  public void testGetId() {
    assertEquals("id", alert.getId());
  }

  @Test
  public void testGetUiFramework() {
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetResource() {
    assertNotNull(alert.getResource());
    assertEquals("/synthetics/parent/forcedResourceName", alert.getResource().getPath());
  }

  @Test
  public void testGetResourceResolver() {
    assertNotNull(alert.getResourceResolver());
  }

  @Test
  public void testGetParentPath() {
    assertEquals("/parent", alert.getParentPath());
  }

  @Test
  public void testGetVariations() {
    assertEquals(0, alert.getVariations().size());
  }

  @Test
  public void testGetInlineVariations() {
    assertEquals("", alert.getInlineVariations());
  }

  @Test
  public void testGetPath() {
    assertEquals("/synthetics/parent/forcedResourceName", alert.getPath());
  }

  @Test
  public void testIsSynthetic() {
    assertTrue(alert.isSynthetic());
  }

  @Test
  public void testIsDataSourceComponent() {
    assertTrue(alert.isDataSourceComponent());
  }

  @Test
  public void testGetRequestAttributes() {

  }

  @Test
  public void testToSyntheticResource() {
  }

  @Test
  public void testTestGetLayout() {
  }

  @Test
  public void testGetComponentResourceType() {
  }

  @Test
  public void testGetForcedResourceName() {
  }

  @Test
  public void testGetComponentVariationRetrievalService() {
  }

  @Test
  public void testGetComponentUiFrameworkViewRetrievalService() {
  }


}