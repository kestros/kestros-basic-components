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

package io.kestros.cms.components.basic.core.content.heading;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Programmatic {@link KestrosHeading}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosHeadingImpl extends BaseSyntheticResource implements KestrosHeading {
  private String text;
  private String headingType;

  /**
   * Constructs a heading impl.
   *
   * @param text Text.
   * @param headingType Heading type.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosHeadingImpl(@Nonnull String text, @Nonnull String headingType,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.text = text;
    this.headingType = headingType;
  }

  /**
   * Constructs a heading impl.
   *
   * @param resource Resource.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosHeadingImpl(@Nonnull Resource resource,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = resource.getValueMap().get("headingText", String.class);
    this.headingType = resource.getValueMap().get("headingType", String.class);
    if (this.text == null || this.headingType == null) {
      throw new ComponentConfigurationException(String.format(
          "Unable to build a heading at %s: headingText and headingType are both required, and%n"
          + " headingText was %s, headingType was %s.",
          resource.getPath(), String.valueOf(this.text), String.valueOf(this.headingType)));
    }
  }

  @Nullable
  @Override
  public String getHeadingText() {
    return text;
  }

  @Nonnull
  @Override
  public String getHeadingType() {
    return headingType;
  }
}
