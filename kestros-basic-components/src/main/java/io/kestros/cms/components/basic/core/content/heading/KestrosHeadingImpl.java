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

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Implementation of the heading element.
 */
public class KestrosHeadingImpl extends BaseSyntheticResource implements KestrosHeading {
  private String text;
  private String headingType;

  /**
   * Builds a heading from the text it is given.
   *
   * @param text Text shown in the heading.
   * @param headingType Heading level the element renders at.
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
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
   * Builds a heading from the properties of the given resource.
   *
   * @param resource Resource the heading properties are read from.
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
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
      throw new ComponentConfigurationException("Missing required property");
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
