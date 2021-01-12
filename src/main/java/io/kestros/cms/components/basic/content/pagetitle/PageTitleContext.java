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

package io.kestros.cms.components.basic.content.pagetitle;

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getResourceAsType;

import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the title of the requested page the the PageTitle component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PageTitleContext extends ComponentRequestContext {

  private static final Logger LOG = LoggerFactory.getLogger(PageTitleContext.class);

  /**
   * Requested page's title.
   *
   * @return Requested page's title.
   */
  @KestrosProperty(description = "Title of the page the component is rendered on.")
  public String getPageTitle() {
    try {
      return getResourceAsType(getRequest().getRequestURI().split(".html")[0],
          getRequest().getResourceResolver(), BaseContentPage.class).getDisplayTitle();
    } catch (ModelAdaptionException e) {
      LOG.warn("Unable to find text for page title component {}. {}.", getBaseResource().getPath(),
          e.getMessage());
    }
    return StringUtils.EMPTY;
  }

}
