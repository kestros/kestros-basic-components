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
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidThemeException;
import io.kestros.cms.uiframeworks.api.exceptions.ThemeRetrievalException;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Synthetic resource for a basic component element that has no stored resource.
 */
public abstract class BaseSyntheticResource extends BaseComponentElement
    implements KestrosBasicComponentElement {
  private final ResourceResolver resourceResolver;
  private final String parentPath;
  private final UiFramework uiFramework;
  private final List<ComponentVariation> componentVariations;
  private final String layout;
  private final String id;
  private Resource syntheticResource;
  private Resource resource;
  private String resourceName;
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  private BaseSlingModelDataSource dataSource;

  /**
   * Builds a synthetic resource for a basic component element.
   *
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
   */
  public BaseSyntheticResource(
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    this.dataSource = dataSource;
    this.resourceResolver = dataSource.getResourceResolver();
    this.parentPath = dataSource.getResource().getPath();
    try {
      this.uiFramework = dataSource.getUiFramework();
    } catch (ResourceNotFoundException | InvalidThemeException | ThemeRetrievalException
        | UiFrameworkRetrievalException exception) {
      throw new ComponentConfigurationException(exception.getMessage());
    }
    this.componentVariations = dataSource.getElementVariations(resourcePrefix + "Variations",
        getComponentResourceType());
    this.layout = dataSource.getLayout(resourcePrefix);
    this.id = null;
    this.resourceName = forcedResourceName;
    if (resourceResolver == null || this.parentPath == null || this.componentVariations == null
        || this.layout == null || this.uiFramework == null) {
      // this is not needed, but is included so that the extending classes are required to throw
      // the exception.
      throw new ComponentConfigurationException("Missing required property");
    }
    this.componentVariationRetrievalService = dataSource.getComponentVariationRetrievalService();
    this.componentUiFrameworkViewRetrievalService =
        dataSource.getComponentUiFrameworkViewRetrievalService();
  }

  @Nullable
  @Override
  public String getId() {
    return this.id;
  }

  @Nonnull
  @Override
  public ResourceResolver getResourceResolver() {
    return resourceResolver;
  }

  @Nonnull
  @Override
  public String getParentPath() {
    return parentPath;
  }

  @Nonnull
  @Override
  public Resource getResource() {
    if (syntheticResource == null) {
      syntheticResource = toSyntheticResource(resourceResolver, parentPath);
    }
    return syntheticResource;
  }

  @Nullable
  @Override
  public SlingHttpServletRequest getRequest() {
    return dataSource.getRequest();
  }

  @Nullable
  @Override
  public String getForcedResourceName() {
    return resourceName;
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getVariations() {
    return new ArrayList<>(componentVariations);
  }

  @Nonnull
  @Override
  public String getLayout() {
    return layout;
  }

  /**
   * UiFramework the synthetic resource renders against.
   *
   * @return UiFramework the synthetic resource renders against.
   */
  @Nonnull
  public UiFramework getUiFramework() {
    return uiFramework;
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }
}
