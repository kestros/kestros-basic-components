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
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Sling Model implementation of {@link KestrosIcon}.
 * Reads icon properties directly from the JCR resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class KestrosIconImpl extends BaseSlingModelDataSource implements KestrosIcon, BaseSlingModel {

  @Nonnull
  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "iconType",
      description = "Rendering mode for the icon: 'font', 'svg', or 'image'. Defaults to 'font'.")
  public String getIconType() {
    return getResource().getValueMap().get("iconType", "font");
  }

  @Nonnull
  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "iconClass",
      description = "CSS class string for font icon mode, e.g. 'fa fa-star'. "
          + "Used when iconType is 'font'.")
  public String getIconClass() {
    return getResource().getValueMap().get("iconClass", "");
  }

  @Nonnull
  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "iconPath",
      description = "DAM asset path for SVG or image mode. "
          + "Used when iconType is 'svg' or 'image'.")
  public String getIconPath() {
    return getResource().getValueMap().get("iconPath", "");
  }

  @Nonnull
  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "altText",
      description = "Alt text for accessibility in SVG and image modes.")
  public String getAltText() {
    return getResource().getValueMap().get("altText", "");
  }

  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "hideFromScreenReader",
      description = "When true, aria-hidden='true' is applied. "
          + "Use for decorative icons that should not be read by screen readers.")
  public boolean getHideFromScreenReader() {
    return getResource().getValueMap().get("hideFromScreenReader", false);
  }

  @Nonnull
  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "href",
      description = "Optional link URL to wrap the icon in an anchor element. "
          + "Applies to image mode.")
  public String getHref() {
    return getResource().getValueMap().get("href", "");
  }

  @Nonnull
  @Override
  @KestrosProperty(configurable = true,
      jcrPropertyName = "sizeClass",
      description = "Optional CSS size modifier class, e.g. 'kbc-icon--sm'.")
  public String getSizeClass() {
    return getResource().getValueMap().get("sizeClass", "");
  }
}
