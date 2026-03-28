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

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListChildPageDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {

  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  public String getRootPath() {
    return getResource().getValueMap().get("pagesPath", String.class);
  }

  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
  }

  @Override
  public List<KestrosLink> getLinkElements() {
    List<BaseContentPage> pages = new ArrayList<>();
    for (Resource childResource : getResourceResolver()
        .getResource(getRootPath()).getChildren()) {
      if (childResource.getName().equals("jcr:content")) {
        continue;
      }
      BaseContentPage page = childResource.adaptTo(BaseContentPage.class);
      if (page != null) {
        pages.add(page);
      }
    }

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", false);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      pages.sort(Comparator.comparing(p -> {
        switch (sortBy) {
          case "name":
            return p.getName() != null ? p.getName() : "";
          default:
            return p.getDisplayTitle() != null ? p.getDisplayTitle() : p.getName();
        }
      }));
    }

    if (reverse) {
      Collections.reverse(pages);
    }
    if (limit > 0 && pages.size() > limit) {
      pages = pages.subList(0, limit);
    }

    List<KestrosLink> links = new ArrayList<>();
    for (BaseContentPage page : pages) {
      KestrosLink link = null;
      try {
        link = new KestrosLinkImpl(page.getDisplayTitle(),
            LinkUtils.getLink(page.getPath()),
            null,
            getTarget(),
            null,
            null,
            null,
            null,
            this,
            "link", null);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      links.add(link);
    }
    return links;
  }

}
