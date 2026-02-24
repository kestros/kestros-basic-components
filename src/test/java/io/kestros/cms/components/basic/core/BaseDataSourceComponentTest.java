package io.kestros.cms.components.basic.core;

import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.BaseComponentTest;
import io.kestros.cms.componenttypes.api.exceptions.ComponentTypeRetrievalException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDataSourceComponentTest extends BaseComponentTest {

  @Override
  public void setupClassLoaderForAllComponentTypes() {
    Map<String, Map<String, String>> allComponentDataSourceMaps = getDataSourceMap();
    for (Map<String, String> componentDataSourceMap : allComponentDataSourceMaps.values()) {
      setupClassLoaderForComponentType(componentDataSourceMap);
    }
  }

  @Override
  public void setupClassLoaderForComponentType(Map<String, String> componentDataSourceMap) {
    for (Map.Entry<String, String> entry : componentDataSourceMap.entrySet()) {
      try {
        Class clazz = Class.forName(entry.getValue());
        when(kestrosClassLoader.getClazz(entry.getValue())).thenReturn(clazz);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }



  public abstract String getResourceType();

  public abstract void doComponentSetup() throws AssetCollectionRetrievalException;

}
