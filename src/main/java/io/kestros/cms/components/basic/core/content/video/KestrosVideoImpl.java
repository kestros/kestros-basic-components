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
package io.kestros.cms.components.basic.core.content.video;

import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosVideoImpl extends BaseSyntheticResource implements KestrosVideo {
  private String videoSource;
  private String fallbackText;

  public KestrosVideoImpl(
      @Nonnull String videoSource,
      @Nonnull String fallbackText,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.videoSource = videoSource;
    this.fallbackText = fallbackText;
  }

  @Nullable
  @Override
  public String getVideoSource() {
    return videoSource;
  }

  @Nonnull
  @Override
  public String getFallbackText() {
    return fallbackText;
  }
}
