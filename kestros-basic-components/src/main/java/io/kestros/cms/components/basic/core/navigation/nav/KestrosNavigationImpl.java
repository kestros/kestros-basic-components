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

package io.kestros.cms.components.basic.core.navigation.nav;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Implementation of the navigation element.
 */
public class KestrosNavigationImpl extends BaseContainerSyntheticResource
    implements KestrosNavigation {

  private List<KestrosNavigationItem> navigationLinks;

  /**
   * Builds a navigation from the items it is given.
   *
   * @param navigationLinks Items the navigation holds.
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
   */
  public KestrosNavigationImpl(
      @Nonnull List<KestrosNavigationItem> navigationLinks,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.navigationLinks = navigationLinks;
  }

  @Nonnull
  @Override
  public List<KestrosNavigationItem> getNavigationLinkElements() {
    return new ArrayList<>(navigationLinks);
  }
}
