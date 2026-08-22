package io.kestros.cms.components.basic.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.exceptions.ComponentElementRenderingException;
import io.kestros.cms.components.basic.core.content.alert.AlertStaticDataSource;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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
    alert = context.resourceResolver().getResource("/").adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert);
    try {
      alert.getParentPath();
      fail("getParentPath returned a value for a resource that has no parent.");
    } catch (ComponentElementRenderingException exception) {
      assertTrue(exception.getMessage(),
          exception.getMessage().contains("the resource has no parent"));
    }
  }

  @Test
  public void testGetVariations() {

  }

  @Test
  public void testGetVariationsWhenResourceDoesNotAdaptToComponent() {
    try {
      dataSourceOnUnadaptableResource().getVariations();
      fail("getVariations returned for a resource that does not adapt to a component.");
    } catch (ComponentElementRenderingException exception) {
      assertTrue(exception.getMessage(),
          exception.getMessage().contains("does not adapt to a"));
    }
  }

  @Test
  public void testGetUiFrameworkWhenResourceDoesNotAdaptToComponent() {
    try {
      dataSourceOnUnadaptableResource().getUiFramework();
      fail("getUiFramework returned for a resource that does not adapt to a component.");
    } catch (ComponentElementRenderingException exception) {
      assertTrue(exception.getMessage(),
          exception.getMessage().contains("Unable to resolve the UI framework"));
    }
  }

  @Test
  public void testGetCurrentOrContainingPageWhenNoPageCanBeResolved() {
    try {
      dataSourceOnUnadaptableResource().getCurrentOrContainingPage();
      fail("getCurrentOrContainingPage returned where no page could be resolved.");
    } catch (ComponentElementRenderingException exception) {
      assertTrue(exception.getMessage(),
          exception.getMessage().contains("Unable to resolve the current or containing page"));
    }
  }

  /**
   * A data source adapted from a resource - so there is no request - whose resource carries no
   * properties and does not adapt to a component. Sling resolves every resource to a component in
   * the mock repository, so the failure has to be built rather than found.
   *
   * @return Data source whose resource adapts to nothing.
   */
  private AlertStaticDataSource dataSourceOnUnadaptableResource() {
    resource = context.create().resource("/content/alert/static/unadaptable", properties);
    AlertStaticDataSource dataSource = spy(resource.adaptTo(AlertStaticDataSource.class));
    Resource unadaptable = mock(Resource.class);
    when(unadaptable.getPath()).thenReturn("/content/alert/static/unadaptable");
    when(unadaptable.getValueMap()).thenReturn(ValueMap.EMPTY);
    when(unadaptable.adaptTo(BaseComponent.class)).thenReturn(null);
    doReturn(unadaptable).when(dataSource).getResource();
    return dataSource;
  }


  @Test
  public void testGetLayout() {
  }

  @Test
  public void testGetForcedResourceName() {
  }

  @Test
  public void testGetUiFramework() {
    resource = context.create().resource("/content/sites/test/jcr:content/alert/static/heading",
        properties);
    context.request().setResource(resource);
    alert = context.request().adaptTo(AlertStaticDataSource.class);
    assertNotNull(alert.getUiFramework());
  }

  @Test
  public void testGetUiFrameworkWhenNoRequest() {
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