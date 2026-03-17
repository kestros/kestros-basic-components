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
import io.kestros.cms.components.basic.api.navigation.KestrosNavigation;
import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class NavigationStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosNavigation {

  @Nonnull
  @Override
  public List<KestrosNavigationItem> getNavigationLinkElements() {
    List<KestrosNavigationItem> links = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (childResource.getValueMap().get("sling:resourceType", StringUtils.EMPTY).equals(
          KestrosNavigationItem.RESOURCE_TYPE)) {
        KestrosNavigationItem link = childResource.adaptTo(NavigationItemStaticDataSource.class);
        if (link != null) {
          links.add(link);
        }
      }
    }
    return new ArrayList<>(links);
  }
}
