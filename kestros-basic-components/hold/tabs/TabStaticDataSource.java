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

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabContent;
import io.kestros.cms.components.basic.api.structure.KestrosTabHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TabStaticDataSource extends BaseSlingModelDataSource implements KestrosTab {

  @Override
  public KestrosTabHeader getTabHeaderElement() {
    try {
      return new KestrosTabHeaderImpl(getTitle(), getContentId(), isActive(), this, "tabHeader",
              "tabHeaderElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


  @Override
  public KestrosTabContent getTabContentElement() {
    try {
      return new KestrosTabContentImpl(this, "tabContent", "tabContentElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


  String getTitle() {
    return getResource().getValueMap().get("tabTitle", String.class);
  }

  String getContentId() {
    return "TODO";
  }

  Boolean isActive() {
    return getResource().getValueMap().get("active", Boolean.class);
  }
}
