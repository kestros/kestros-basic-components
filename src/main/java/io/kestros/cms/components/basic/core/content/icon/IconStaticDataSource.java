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

package io.kestros.cms.components.basic.core.content.icon;

import io.kestros.cms.components.basic.api.content.KestrosIcon;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Static Data Source for the Icon component.
 * Reads icon properties directly from the JCR resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class IconStaticDataSource extends BaseSlingModelDataSource implements KestrosIcon {

  @Nonnull
  @Override
  public String getIconType() {
    return getResource().getValueMap().get("iconType", "font");
  }

  @Nonnull
  @Override
  public String getIconClass() {
    return getResource().getValueMap().get("iconClass", "");
  }

  @Nonnull
  @Override
  public String getIconPath() {
    return getResource().getValueMap().get("iconPath", "");
  }

  @Nonnull
  @Override
  public String getAltText() {
    return getResource().getValueMap().get("altText", "");
  }

  @Override
  public boolean getHideFromScreenReader() {
    return getResource().getValueMap().get("hideFromScreenReader", false);
  }

  @Nonnull
  @Override
  public String getHref() {
    return getResource().getValueMap().get("href", "");
  }

  @Nonnull
  @Override
  public String getSizeClass() {
    return getResource().getValueMap().get("sizeClass", "");
  }
}
