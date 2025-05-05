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

package io.kestros.cms.components.basic.structure.breadcrumb;

import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.BasePage;
import io.kestros.commons.structuredslingmodels.exceptions.NoParentResourceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Breadcrumb component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class BreadcrumbsComponentContext extends ComponentRequestContext {

  public List<BreadcrumbsCrumb> getCrumbs() {
    List<BreadcrumbsCrumb> crumbs = new ArrayList<>();
    boolean currentPage = true;
    for (Resource crumb : getCrumbResources()) {
      BasePage page = crumb.adaptTo(BasePage.class);
      if (page != null) {
        crumbs.add(new BreadcrumbsCrumb(page, currentPage));
        currentPage = false;
      }
    }
    Collections.reverse(crumbs);
    return crumbs;
  }


  List<Resource> getCrumbResources() {
    List<Resource> crumbs = new ArrayList<>();
    BasePage currentPage = getCurrentPage();
    boolean isSiteRoot = currentPage.getJcrPrimaryType().equals("kes:Site");
    do {
      crumbs.add(currentPage.getResource());
      try {
        isSiteRoot = currentPage.getJcrPrimaryType().equals("kes:Site");
        currentPage = currentPage.getParent().getResource().adaptTo(BaseContentPage.class);
      } catch (NoParentResourceException e) {
        currentPage = null;
      }
    } while (currentPage != null && !isSiteRoot);
    return crumbs;
  }
}