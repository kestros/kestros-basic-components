/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
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
