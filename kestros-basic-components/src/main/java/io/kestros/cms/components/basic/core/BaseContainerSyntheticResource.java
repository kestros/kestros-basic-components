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

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

/**
 * Synthetic resource for an element that holds other elements.
 */
public abstract class BaseContainerSyntheticResource extends BaseSyntheticResource
    implements KestrosContainerElement {

  /**
   * Builds a synthetic resource for an element that holds other elements.
   *
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
   */
  public BaseContainerSyntheticResource(
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    List<Resource> children = new ArrayList<>();
    for (KestrosBasicComponentElement componentElement : getChildElements()) {
      children.add(componentElement.toSyntheticResource(getResourceResolver(), getPath()));
    }
    return children;
  }

}
