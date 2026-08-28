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
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Component that renders a top navigation from the data its datasource supplies.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TopNavigationDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosTopNavigation> implements KestrosTopNavigation {

  private List<KestrosTopNavigationItem> navigationLinks;
  private KestrosImage logo;

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationLinkElements() {
    if (navigationLinks == null) {
      navigationLinks = getComponentData().getNavigationLinkElements();
    }
    return navigationLinks;
  }

  @Nullable
  @Override
  public String getBrandName() {
    return getComponentData().getBrandName();
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
      if (logo == null) {
      logo = getComponentData().getImageElement();
    }
    return logo;
  }
}
