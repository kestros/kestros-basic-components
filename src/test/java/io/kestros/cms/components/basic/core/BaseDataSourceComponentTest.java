package io.kestros.cms.components.basic.core;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.BaseComponentTest;
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
    // KestrosClassLoader was removed from the API — no-op; class loading now uses Class.forName directly
  }



  public abstract String getResourceType();

  public abstract void doComponentSetup() throws AssetCollectionRetrievalException;

}
