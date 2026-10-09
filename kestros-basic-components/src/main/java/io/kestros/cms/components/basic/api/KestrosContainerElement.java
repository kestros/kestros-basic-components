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

package io.kestros.cms.components.basic.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Basic component element that holds other elements.
 */
public interface KestrosContainerElement extends KestrosBasicComponentElement {

  /**
   * ResourceResolver the container reads through.
   *
   * @return ResourceResolver the container reads through.
   */
  @JsonIgnore
  @Nonnull
  ResourceResolver getResourceResolver();

  /**
   * Resource the container was built from.
   *
   * @return Resource the container was built from.
   */
  @JsonIgnore
  @Nonnull
  Resource getResource();

  /**
   * Resources of the elements the container holds.
   *
   * @return Resources of the elements the container holds.
   */
  @JsonIgnore
  @Nonnull
  List<Resource> getChildren();

  /**
   * Elements the container holds.
   *
   * @return Elements the container holds.
   */
  @JsonIgnore
  @Nonnull
  List<KestrosBasicComponentElement> getChildElements();

}
