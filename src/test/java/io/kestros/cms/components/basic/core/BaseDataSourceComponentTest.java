package io.kestros.cms.components.basic.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.componenttypes.api.exceptions.ComponentTypeRetrievalException;
import io.kestros.cms.componenttypes.api.models.ComponentType;
import io.kestros.cms.componenttypes.api.services.ComponentTypeRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.services.KestrosClassLoader;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;

public abstract class BaseDataSourceComponentTest {
  @Rule
  public SlingContext context = new SlingContext();
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  private ComponentTypeRetrievalService componentTypeRetrievalService;
  private KestrosClassLoader kestrosClassLoader;


  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    componentUiFrameworkViewRetrievalService = mock(ComponentUiFrameworkViewRetrievalService.class);
    componentVariationRetrievalService = mock(ComponentVariationRetrievalService.class);
    kestrosClassLoader = mock(KestrosClassLoader.class);
    componentTypeRetrievalService = mock(ComponentTypeRetrievalService.class);

    context.registerService(ComponentUiFrameworkViewRetrievalService.class,
            componentUiFrameworkViewRetrievalService);
    context.registerService(ComponentVariationRetrievalService.class,
            componentVariationRetrievalService);
    context.registerService(ComponentTypeRetrievalService.class, componentTypeRetrievalService);
    context.registerService(KestrosClassLoader.class, kestrosClassLoader);
    this.doComponentTypeSetup();
    this.setupClassLoader();
    this.doComponentSetup();
  }

  public void setupClassLoader() {
    for (Map.Entry<String, String> entry : getDataSourceMap().entrySet()) {
      try {
        Class clazz = Class.forName(entry.getValue());
        when(kestrosClassLoader.getClazz(entry.getValue())).thenReturn(clazz);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  public void doComponentTypeSetup() throws ComponentTypeRetrievalException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(getResourceType(), properties);
    context.create().resource(getResourceType() + "/datasources");
    for (Map.Entry<String, String> entry : getDataSourceMap().entrySet()) {
      Map<String, Object> dsProperties = new HashMap<>();
      dsProperties.put("jcr:primaryType", "nt:unstructured");
      dsProperties.put("classPath", entry.getValue());
      context.create().resource(getResourceType() + "/datasources/" + entry.getKey(), dsProperties);
    }
    Resource componentTypeResource = context.resourceResolver()
            .getResource(getResourceType());
    ComponentType componentType = componentTypeResource.adaptTo(
            io.kestros.cms.componenttypes.core.models.ComponentTypeResource.class);
    when(componentTypeRetrievalService.getComponentType(eq(getResourceType()), any()))
            .thenReturn(componentType);

  }

  public abstract Map<String, String> getDataSourceMap();

  public abstract String getResourceType();

  public abstract void doComponentSetup();

}
