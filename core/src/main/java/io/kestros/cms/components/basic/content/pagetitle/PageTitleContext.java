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

import io.kestros.cms.foundation.content.pages.BaseContentPage;
import io.kestros.commons.structuredslingmodels.BaseSlingRequest;
import io.kestros.commons.structuredslingmodels.exceptions.InvalidResourceTypeException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

/**
 * Provides the title of the requested page the the PageTitle component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PageTitleContext extends BaseSlingRequest {

  /**
   * Requested page's title.
   *
   * @return Requested page's title.
   */
  public String getText() {
    try {
      return SlingModelUtils.getResourceAsType(getRequest().getRequestURI().split(".html")[0],
          getRequest().getResourceResolver(), BaseContentPage.class).getDisplayTitle();
    } catch (InvalidResourceTypeException e) {
      e.printStackTrace();
    } catch (ResourceNotFoundException e) {
      e.printStackTrace();
    }
    return StringUtils.EMPTY;
  }

}
