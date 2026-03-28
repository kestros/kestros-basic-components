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

package io.kestros.cms.components.basic.core.structure.section;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosSection;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.structure.container.KestrosContainerImpl;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosSectionImpl extends KestrosContainerImpl implements KestrosSection {

  private String backgroundImage;

  public KestrosSectionImpl(
      @Nullable String backgroundImage,
      @Nonnull List<KestrosBasicComponentElement> childElements,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(childElements, dataSource, resourcePrefix, forcedResourceName);
    this.backgroundImage = backgroundImage;
  }

  @Nullable
  @Override
  public String getBackgroundImage() {
    return backgroundImage;
  }
}
