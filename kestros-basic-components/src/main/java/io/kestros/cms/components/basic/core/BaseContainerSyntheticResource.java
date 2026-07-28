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
 * The base container synthetic resource component element.
 */
public abstract class BaseContainerSyntheticResource extends BaseSyntheticResource
    implements KestrosContainerElement {

  /**
   * Constructs a base container synthetic resource.
   *
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
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
    final List<KestrosBasicComponentElement> sourceChildren = getChildElements();
    final List<Resource> children = new ArrayList<>(sourceChildren.size());
    for (KestrosBasicComponentElement componentElement : sourceChildren) {
      children.add(componentElement.toSyntheticResource(getResourceResolver(), getPath()));
    }
    return children;
  }

}
