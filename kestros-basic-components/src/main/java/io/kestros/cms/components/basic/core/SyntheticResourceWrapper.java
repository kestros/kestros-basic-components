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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;

/**
 * ResourceWrapper that allows for synthetic children to be added to the wrapped Resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_EQUALS")
public class SyntheticResourceWrapper extends ResourceWrapper {

  private final Map<String, Resource> children;

  /**
   * SyntheticResourceWrapper Constructor.
   *
   * @param wrapped Wrapped Resource.
   * @param children Map of synthetic children to add to the wrapped Resource.
   */
  public SyntheticResourceWrapper(Resource wrapped, @Nonnull Map<String, Resource> children) {
    super(wrapped);
    this.children = new HashMap<>(children);
  }

  @Nullable
  @Override
  public Resource getChild(@Nullable String name) {
    Resource child = children.get(name);
    return child != null ? child : super.getChild(name);
  }

  @Nonnull
  @Override
  public Iterator<Resource> listChildren() {
    return children.values().iterator();
  }

  @Nonnull
  @Override
  public Iterable<Resource> getChildren() {
    return children.values();
  }
}
