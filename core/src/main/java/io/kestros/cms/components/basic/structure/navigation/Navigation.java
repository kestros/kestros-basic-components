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

package io.kestros.cms.components.basic.structure.navigation;

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getFirstAncestorOfType;
import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getParentResourceAsType;
import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getResourceAsType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.kestros.cms.foundation.content.BaseComponent;
import io.kestros.cms.foundation.content.pages.BaseContentPage;
import io.kestros.cms.foundation.content.sites.BaseSite;
import io.kestros.commons.structuredslingmodels.exceptions.InvalidResourceTypeException;
import io.kestros.commons.structuredslingmodels.exceptions.NoParentResourceException;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class,
       resourceType = {"kestros/commons/components/structure/navigation"})
public class Navigation extends BaseComponent {

  private static final Logger LOG = LoggerFactory.getLogger(Navigation.class);

  @JsonIgnoreProperties({"components", "childPages"})
  public List<BaseContentPage> getNavigationPages() {
    try {
      return getRootPage().getChildPages();
    } catch (NoValidAncestorException e) {
      LOG.error("Failed to build child pages for Navigation component {}. {}", getPath(),
          e.getMessage());
    }
    return Collections.emptyList();
  }

  @JsonIgnore
  public BaseContentPage getRootPage() throws NoValidAncestorException {
    String rootPagePath = getProperty("rootPage", StringUtils.EMPTY);

    try {
      return getResourceAsType(rootPagePath, getResourceResolver(), BaseContentPage.class);
    } catch (InvalidResourceTypeException e) {
      try {
        return getResourceAsType(rootPagePath, getResourceResolver(), BaseSite.class);
      } catch (ResourceNotFoundException | InvalidResourceTypeException ex) {
        LOG.trace(
            "Failed to retrieve root page for Navigation component {} when adapting to site. {}. "
            + "Looking to containing page.", getPath(), e.getMessage());
      }
    } catch (ResourceNotFoundException e) {
      LOG.error("Failed to retrieve root page for Navigation component {}. {}", getPath(),
          e.getMessage());
    }

    if (!this.getContainingPage().getChildPages().isEmpty()) {
      return this.getContainingPage();
    } else {
      try {
        return getParentResourceAsType(this.getContainingPage(), BaseContentPage.class);
      } catch (NoParentResourceException | InvalidResourceTypeException e) {
        LOG.trace("Failed to retrieve root page for Navigation component {}. Containing page was "
                  + "invalid. {}.", getPath(), e.getMessage());
      }
    }
    return getFirstAncestorOfType(this, BaseSite.class);
  }

}
