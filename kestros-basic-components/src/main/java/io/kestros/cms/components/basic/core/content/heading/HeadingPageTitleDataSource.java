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

package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Datasource that builds a heading from the title of the page being rendered.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class HeadingPageTitleDataSource extends HeadingStaticDataSource implements KestrosHeading {
  private static final Logger LOG = LoggerFactory.getLogger(HeadingPageTitleDataSource.class);

  @Self
  @Optional
  private SlingHttpServletRequest request;

  BaseContentPage getPage() {
    try {
      if (isOverrideInheritedTitle()) {
        if (request != null) {
          return request.adaptTo(ComponentRequestContext.class).getCurrentPage();
        } else {
          throw new RuntimeException(
                  "SlingHttpServletRequest is required to override inherited title.");
        }
      } else {
        return request.getResource().adaptTo(BaseComponent.class).getContainingPage();
      }
    } catch (ModelAdaptionException e) {
      LOG.warn("Unable to find text for page title component {}. {}.", getResource().getPath(),
              e.getMessage());
      return null;
    }
  }

  @Nullable
  @Override
  public String getHeadingText() {
    return getPage().getDisplayTitle();
  }

  /**
   * Whether the configured title replaces the one inherited from the page.
   *
   * @return Whether the configured title replaces the one inherited from the page.
   */
  public Boolean isOverrideInheritedTitle() {
    return getResource().getValueMap().get("overrideInheritedTitle", Boolean.FALSE);
  }
}
