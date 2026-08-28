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

package io.kestros.cms.components.basic.core.navigation.topnav;

import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of the top navigation element.
 */
public class KestrosTopNavigationImpl extends BaseContainerSyntheticResource
        implements KestrosTopNavigation {

  private List<KestrosTopNavigationItem> navigationLinks;
  private KestrosImage logo;
  private String brandName;

  public KestrosTopNavigationImpl(
          @Nullable String brandName,
          @Nullable KestrosImage logo,
          @Nonnull List<KestrosTopNavigationItem> navigationLinks,
          @Nonnull BaseSlingModelDataSource dataSource,
          @Nonnull String resourcePrefix, String forcedResourceName)
          throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.logo = logo;
    this.brandName = brandName;
    this.navigationLinks = navigationLinks;
  }

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationLinkElements() {
    return new ArrayList<>(navigationLinks);
  }

  @Nullable
  @Override
  public String getBrandName() {
    return brandName;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    return logo;
  }
}
