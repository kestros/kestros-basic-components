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

package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * API for the top navigation item component element.
 */
public interface KestrosTopNavigationItem extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/top-navigation-item";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }


  /**
   * Navigation items.
   *
   * @return Navigation items.
   */
  @Nonnull
  List<KestrosTopNavigationItem> getNavigationItems();

  /**
   * Navigation item links.
   *
   * @return Navigation item links.
   */
  @Nonnull
  default List<Resource> getNavigationItemLinks() {
    final List<KestrosTopNavigationItem> sourceResources = getNavigationItems();
    final List<Resource> resources = new ArrayList<>(sourceResources.size());
    for (KestrosTopNavigationItem item : sourceResources) {
      resources.add(item.getResource());
    }
    return resources;
  }

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationItems());
  }

  /**
   * Active.
   *
   * @return Active.
   */
  @Nonnull
  default Boolean isActive() {
    if (getRequest() != null) {
      String currentPath = getRequest().getRequestURI();
      String linkPath = getHref();
      if (linkPath != null && !linkPath.isBlank()) {
        return currentPath.equals(linkPath);
      }
    }
    return Boolean.FALSE;
  }

  /**
   * Visible link text. This should normally be present and meaningful.
   *
   * @return Visible link text.
   */
  @Nullable
  String getText();

  /**
   * Destination URL. Should be null if the link is not navigable.
   *
   * @return Destination URL.
   */
  @Nullable
  String getHref();

  /**
   * Target attribute (e.g. "_self", "_blank").
   *
   * @return Target attribute (e.g.
   */
  @Nonnull
  AnchorTarget getTarget();

  /**
   * Get the target as a string.
   *
   * @return Target attribute value.
   */
  @Nonnull
  default String getTargetAsString() {
    if (getTarget() != null) {
      return getTarget().getTargetValue();
    }
    return AnchorTarget.SAME_WINDOW.getTargetValue();
  }

  /**
   * ARIA label. Used when visible text is missing or insufficient.
   *
   * @return ARIA label.
   */
  @Nullable
  String getAriaLabel();

  /**
   * Optional title attribute. Use sparingly – should add meaning, not duplicate text.
   *
   * @return Optional title attribute.
   */
  @Nullable
  String getTitle();

  /**
   * Relationship attribute (e.g. "noopener", "noreferrer", "nofollow").
   *
   * @return Relationship attribute (e.g.
   */
  @Nullable
  String getRel();

  /**
   * ARIA described-by ID reference.
   *
   * @return ARIA described-by ID reference.
   */
  @Nullable
  String getAriaDescribedBy();

  /**
   * Language of the link text, if different from the page language. Example: "fr", "de", "en-GB"
   *
   * @return Language of the link text, if different from the page language.
   */
  @Nullable
  String getLang();

}