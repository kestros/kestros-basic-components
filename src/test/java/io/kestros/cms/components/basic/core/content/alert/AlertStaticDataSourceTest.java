package io.kestros.cms.components.basic.core.content.alert;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AlertStaticDataSourceTest {

  @Rule
  public SlingContext context = new SlingContext();
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  private AlertStaticDataSource alertStaticDataSource;
  private Resource resource;
  private Map<String,Object> properties = new HashMap<>();
  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    componentUiFrameworkViewRetrievalService = mock(ComponentUiFrameworkViewRetrievalService.class);
    componentVariationRetrievalService = mock(ComponentVariationRetrievalService.class);
    context.registerService(ComponentUiFrameworkViewRetrievalService.class, componentUiFrameworkViewRetrievalService);
    context.registerService(ComponentVariationRetrievalService.class, componentVariationRetrievalService);

    properties.put("heading","Test Heading");
    properties.put("text","Test Text");
    resource = context.create().resource("/content/alert/static/heading", properties);
    context.request().setResource(resource);
    alertStaticDataSource = context.request().adaptTo(AlertStaticDataSource.class);
  }

  @Test
  public void testGetHeading() {
    String heading = alertStaticDataSource.getHeading();
    assertEquals("Test Heading", heading);
  }

  @Test
  public void testGetText() {
    String text = alertStaticDataSource.getText();
    assertEquals("Test Text", text);
  }
}