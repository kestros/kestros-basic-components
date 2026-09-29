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

package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Base Sling Model Data Source for Kestros Container Elements.
 */
public abstract class BaseContainerSlingModelDataSource extends BaseSlingModelDataSource
    implements KestrosContainerElement {

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    List<Resource> children = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getChildElements()) {
      if (element.isSynthetic()) {
        children.add(element.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        children.add(element.getResource());
      }
    }
    return children;
  }

  /**
   * Child resources of the given resource type, adapted to the given model.
   *
   * @param <T> Model the child resources are adapted to.
   * @param resourceType Resource type the children must match.
   * @param clazz Model the child resources are adapted to.
   * @return Child resources of the given resource type, adapted to the given model.
   */
  @Nonnull
  public <T extends KestrosBasicComponentElement> List<T> getChildrenAsType(
      @Nonnull String resourceType, @Nonnull Class<T> clazz) {
    List<T> items = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (!childResource.isResourceType(resourceType)) {
        continue;
      }
      T item = childResource.adaptTo(clazz);
      if (item != null) {
        items.add(item);
      }
    }
    return new ArrayList<>(items);
  }

  /**
   * Child elements that could be adapted to the given model.
   *
   * @param <T> Model the child elements are adapted to.
   * @param clazz Model the child elements are adapted to.
   * @return Child elements that could be adapted to the given model.
   */
  @Nonnull
  public <T extends KestrosBasicComponentElement> List<T> getChildrenOfType(
      @Nonnull Class<T> clazz) {
    List<T> children = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getChildElements()) {
      if (clazz.isInstance(element)) {
        children.add(clazz.cast(element));
      }
    }
    return children;
  }

}
