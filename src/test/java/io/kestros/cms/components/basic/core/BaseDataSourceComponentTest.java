package io.kestros.cms.components.basic.core;

import static org.mockito.Mockito.when;

import io.kestros.cms.components.basic.BaseComponentTest;
import io.kestros.cms.componenttypes.api.exceptions.ComponentTypeRetrievalException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;

public abstract class BaseDataSourceComponentTest extends BaseComponentTest {

  @Override
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

  @Override
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
  }

  public abstract Map<String, String> getDataSourceMap();

  public abstract String getResourceType();

  public abstract void doComponentSetup();

}
