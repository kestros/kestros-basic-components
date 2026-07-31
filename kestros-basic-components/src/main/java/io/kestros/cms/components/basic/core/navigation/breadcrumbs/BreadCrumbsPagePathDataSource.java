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

package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Supplies {@link KestrosBreadCrumbs} built from page path.
 */
@SuppressFBWarnings("FCBL_FIELD_COULD_BE_LOCAL")
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class BreadCrumbsPagePathDataSource extends BaseSlingModelDataSource
    implements KestrosBreadCrumbs {

  private static final Logger LOG =
      LoggerFactory.getLogger(BreadCrumbsPagePathDataSource.class);

  @Self
  @Optional
  private SlingHttpServletRequest slingHttpServletRequest;

  @Self
  @Optional
  private Resource resource;

  @Nonnull
  @Override
  public List<KestrosBreadCrumb> getLinkElements() {
    List<KestrosBreadCrumb> crumbs = new ArrayList<>();
    boolean first = true;
    boolean last = false;
    int index = 0;
    List<BaseContentPage> ancestorPages = getAncestorPages();
    for (BaseContentPage page : ancestorPages) {
      try {
        if (index == ancestorPages.size() - 1) {
          last = true;
        }
        KestrosLink link = new KestrosLinkImpl(page, this, "crumb", page.getName());

        KestrosBreadCrumb crumb = new KestrosBreadCrumbImpl(link, first, last, this, "crumb",
            page.getName());
        crumbs.add(crumb);
        first = false;
        index++;
      } catch (ComponentConfigurationException e) {
        LOG.debug("Skipping a breadcrumb for {}: it could not be built. {}",
            String.valueOf(page.getPath()).replaceAll("[\r\n]", ""),
            String.valueOf(e.getMessage()).replaceAll("[\r\n]", ""));
      }
    }
    return new ArrayList<>(crumbs);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    // todo this.
    return List.of();
  }

  @Nonnull
  List<BaseContentPage> getAncestorPages() {
    List<BaseContentPage> pages = new ArrayList<>();
    BaseContentPage page = getCurrentOrContainingPage();
    while (page != null) {
      pages.add(0, page);
      try {
        page = page.getParent();
      } catch (Exception e) {
        page = null;
      }
    }
    return pages;
  }
}
