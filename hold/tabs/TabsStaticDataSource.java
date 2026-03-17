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
package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabs;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TabsStaticDataSource extends BaseSlingModelDataSource implements KestrosTabs {
  @Override
  public List<KestrosTab> getTabs() {
    List<KestrosTab> tabs = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosTab tab = childResource.adaptTo(TabStaticDataSource.class);
      if (tab != null) {
        tabs.add(tab);
      }
    }
    return new ArrayList<>(tabs);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    return List.of();
  }
}
