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

package io.kestros.cms.components.basic.core.lists.linklist;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.LinkStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {
  @Nonnull
  @Override
  public List<KestrosLink> getLinkElements() {
    List<KestrosLink> links = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosLink link = childResource.adaptTo(LinkStaticDataSource.class);
      if (link != null) {
        links.add(link);
      }
    }
    return new ArrayList<>(links);
  }
}
