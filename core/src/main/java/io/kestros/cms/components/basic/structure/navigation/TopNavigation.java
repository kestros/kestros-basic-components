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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.foundation.content.pages.BaseContentPage;
import io.kestros.cms.foundation.content.sites.BaseSite;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Top Navigation which acts the same as {@link Navigation}, but the Site root is always the
 * RootPage.
 */
@Model(adaptables = Resource.class,
       resourceType = {"kestros/commons/components/structure/top-navigation"})
public class TopNavigation extends Navigation {

  @JsonIgnore
  @Override
  public BaseContentPage getRootPage() throws NoValidAncestorException {
    return getFirstAncestorOfType(this, BaseSite.class);
  }

}
