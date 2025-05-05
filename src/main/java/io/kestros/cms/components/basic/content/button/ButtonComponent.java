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

package io.kestros.cms.components.basic.content.button;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.commonutils.jcr.JcrPropertyUtils;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

/**
 * Basic button component.
 */
@KestrosModel(contextModel = ComponentRequestContext.class)
@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/button")
public class ButtonComponent extends BaseComponent {

  @Self
  private Resource resource;

  @Nonnull
  public String getLabel() {
    return resource.getValueMap().get("label", StringUtils.EMPTY);
  }

  @Nullable
  public String getHref() {
    if (isLink()) {
      if (isLinkExternal()) {
        return getHrefPropertyValue();
      } else {
        return getLinkResource().getPath() + ".html";
      }
    }
    return null;
  }

  @Nonnull
  public String getTarget() {
    return resource.getValueMap().get("target", "_self");
  }

  @Nonnull
  public String getAriaLabel() {
    return resource.getValueMap().get("ariaLabel", StringUtils.EMPTY);
  }

  @Nonnull
  public String getAnchorTitle() {
    return JcrPropertyUtils.getStringOrDefaultValue(getResource(),
        "anchorTitle", StringUtils.EMPTY);
  }

  boolean isLink() {
    return !StringUtils.isEmpty(getHrefPropertyValue());
  }

  boolean isLinkExternal() {
    return StringUtils.isNotEmpty(getHrefPropertyValue()) && !getHrefPropertyValue().startsWith(
        "/");
  }

  String getHrefPropertyValue() {
    return getProperty("href", StringUtils.EMPTY);
  }

  Resource getLinkResource() {
    if (isLink() && !isLinkExternal()) {
      return getResourceResolver().getResource(getHrefPropertyValue());
    }
    return null;
  }


}
