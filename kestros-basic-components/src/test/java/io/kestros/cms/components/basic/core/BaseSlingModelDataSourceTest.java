package io.kestros.cms.components.basic.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.content.alert.AlertStaticDataSource;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidThemeException;
import io.kestros.cms.uiframeworks.api.exceptions.ThemeRetrievalException;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class BaseSlingModelDataSourceTest extends BaseDataSourceTest {

  private AlertStaticDataSource alert;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    // test page already set up
  }

  @Override
  public void testToSyntheticResource() {

  }

  @Test
  public void testGetId() {
    properties.put("id", "ID");
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertEquals("ID", alert.getId());
  }

  @Test
  public void testIsSynthetic() {
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertFalse(alert.isSynthetic());
  }

  @Test
  public void testGetResource() {
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getResource());
    assertEquals("/content/alert/static/heading", alert.getResource().getPath());
  }

  @Test
  public void testGetCurrentOrContainingPage() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getCurrentOrContainingPage());
    assertEquals("/content/sites/test", alert.getCurrentOrContainingPage().getPath());
  }

  @Test
  public void testGetCurrentOrContainingPageWhenRequestIsNull() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    alert = resource.adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getCurrentOrContainingPage());
    assertEquals("/content/sites/test", alert.getCurrentOrContainingPage().getPath());
  }

  @Test
  public void testGetResourceResolver() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getResourceResolver());
  }

  @Test
  public void testGetParentPath() {
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertEquals("/content/alert/static", alert.getParentPath());
  }

  @Test
  public void testGetParentPathWhenResourceHasNoParent() {
    resource = context.resourceResolver().getResource("/");
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    try {
      alert.getParentPath();
      fail("Expected IllegalStateException when the Resource has no parent.");
    } catch (IllegalStateException exception) {
      assertEquals("Resource / has no parent.", exception.getMessage());
    }
  }

  @Test
  public void testGetVariations() {

  }

  @Test
  public void testGetVariationsWhenResourceDoesNotAdaptToComponent() {
    resource = context.resourceResolver().getResource("/");
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertTrue(alert.getVariations().isEmpty());
  }

  @Test
  public void testGetLayout() {
  }

  @Test
  public void testGetForcedResourceName() {
  }

  @Test
  public void testGetUiFramework() throws ResourceNotFoundException, InvalidThemeException,
      ThemeRetrievalException, UiFrameworkRetrievalException {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetUiFrameworkWhenNoRequest() throws ResourceNotFoundException,
      InvalidThemeException, ThemeRetrievalException, UiFrameworkRetrievalException {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    alert = resource.adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetComponentVariationRetrievalService() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getComponentVariationRetrievalService());
  }

  @Test
  public void testGetComponentUiFrameworkViewRetrievalService() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getComponentUiFrameworkViewRetrievalService());
  }
}