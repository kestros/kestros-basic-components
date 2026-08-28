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
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidThemeException;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidUiFrameworkException;
import io.kestros.cms.uiframeworks.api.exceptions.ThemeRetrievalException;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Behaviour shared by every basic component element, whether it is backed by a real resource or
 * built synthetically at request time.
 */
public interface KestrosBasicComponentElement {

  /**
   * Layout configured on the given property.
   *
   * @param propertyName Property to read the layout from.
   * @return Layout configured on the given property.
   */
  String getLayout(String propertyName);

  /**
   * Layout the element renders with.
   *
   * @return Layout the element renders with.
   */
  @Nonnull
  String getLayout();

  /**
   * Variations applied to the given property, for the given component type.
   *
   * @param propertyName Property the variation names are read from.
   * @param componentType Component type the variations belong to.
   * @return Variations applied to the given property, for the given component type.
   */
  List<ComponentVariation> getElementVariations(String propertyName, String componentType);

  //  @Deprecated
  //  static List<ComponentVariation> getAppliedVariations(String propertyName,
  //      Resource resource,
  //      String componentTypePath,
  //      UiFramework uiFramework,
  //      ComponentVariationRetrievalService componentVariationRetrievalService,
  //      ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService) {
  //
  //    BaseComponent component = resource.adaptTo(BaseComponent.class);
  //    final List<ComponentVariation> appliedVariations = new ArrayList<>();
  //    Object propertyValue = resource.getValueMap().get(propertyName);
  //    // if list of maps
  //    final List<String> appliedVariationNames;
  //    if (propertyValue instanceof List && !((List<?>) propertyValue).isEmpty()
  //        && ((List<?>) propertyValue).get(0) instanceof Map) {
  //      // TODO checking the map here is a bit hacky, but not sure of a better way.
  //      List<Map<String, Object>> variationMaps = (List<Map<String, Object>>) propertyValue;
  //      appliedVariationNames = new ArrayList<>();
  //      for (Map<String, Object> variationMap : variationMaps) {
  //        appliedVariationNames.add((String) variationMap.get("path"));
  //      }
  //    } else {
  //      appliedVariationNames = JcrPropertyUtils.getStringListOrEmptyList(
  //          resource,
  //          propertyName);
  //    }
  //
  //
  //    try {
  //
  //      final ComponentUiFrameworkView uiFrameworkView
  //          = componentUiFrameworkViewRetrievalService.getResolvedComponentUiFrameworkView(
  //          componentTypePath, uiFramework, component.getResourceResolver());
  //      List<ComponentVariation> variations
  //          = componentVariationRetrievalService.getComponentVariations(uiFrameworkView);
  //      if (!appliedVariationNames.isEmpty()) {
  //        for (final String appliedVariation : appliedVariationNames) {
  //          for (final ComponentVariation variation : variations) {
  //            if (variation.getPath().equals(appliedVariation) || variation.getName()
  //                .equals(appliedVariation)) {
  //              appliedVariations.add(variation);
  //            }
  //          }
  //        }
  //      }
  //
  //      if (appliedVariationNames.isEmpty() && !resource.getValueMap()
  //          .containsKey(propertyName)) {
  //        for (ComponentVariation variation : variations) {
  //          if (variation.isDefault()) {
  //            appliedVariations.add(variation);
  //          }
  //        }
  //      }
  //    } catch (final ModelAdaptionException exception) {
  //    } catch (final ComponentViewRetrievalException e) {
  //
  //    }
  //    return new ArrayList<>(appliedVariations);
  //  }

  /**
   * Unique id rendered onto the element, if one was configured.
   *
   * @return Unique id rendered onto the element, if one was configured.
   */
  @Nullable
  String getId();

  /**
   * UiFramework the element renders against.
   *
   * @return UiFramework the element renders against.
   * @throws InvalidThemeException Theme could not be adapted.
   * @throws ResourceNotFoundException Expected resource was missing.
   * @throws InvalidUiFrameworkException UiFramework could not be adapted.
   * @throws ThemeRetrievalException Theme could not be retrieved.
   * @throws UiFrameworkRetrievalException UiFramework could not be retrieved.
   * @throws ModelAdaptionException Resource could not be adapted to the expected model.
   */
  @JsonIgnore
  UiFramework getUiFramework() throws InvalidThemeException, ResourceNotFoundException,
      InvalidUiFrameworkException, ThemeRetrievalException, UiFrameworkRetrievalException,
      ModelAdaptionException;

  /**
   * Resource the element was built from.
   *
   * @return Resource the element was built from.
   */
  @JsonIgnore
  Resource getResource();

  /**
   * Request the element is rendering within, if there is one.
   *
   * @return Request the element is rendering within, if there is one.
   */
  @JsonIgnore
  @Nullable
  SlingHttpServletRequest getRequest();

  /**
   * ResourceResolver the element reads through.
   *
   * @return ResourceResolver the element reads through.
   */
  @JsonIgnore
  ResourceResolver getResourceResolver();

  /**
   * Path of the resource that contains this element.
   *
   * @return Path of the resource that contains this element.
   */
  @JsonIgnore
  String getParentPath();

  /**
   * Variations applied to the element.
   *
   * @return Variations applied to the element.
   */
  List<ComponentVariation> getVariations();

  /**
   * Names of the applied variations that render inline, separated by spaces.
   *
   * @return Names of the applied variations that render inline, separated by spaces.
   */
  default String getInlineVariations() {
    StringJoiner joiner = new StringJoiner(" ");
    for (ComponentVariation variation : getVariations()) {
      if (variation.isInlineVariation()) {
        joiner.add(variation.getName());
      }
    }
    return joiner.toString();
  }

  /**
   * Path the element resolves to. Synthetic elements are namespaced under /synthetics.
   *
   * @return Path the element resolves to.
   */
  @JsonIgnore
  @Nonnull
  default String getPath() {
    if (isSynthetic()) {
      if (getResource().getPath().startsWith("/synthetics")) {
        return getResource().getPath();
      }
      return "/synthetics" + getResource().getPath();
    }
    return getResource().getPath();
  }

  /**
   * Whether the element is synthetic rather than backed by a stored resource.
   *
   * @return Whether the element is synthetic rather than backed by a stored resource.
   */
  default Boolean isSynthetic() {
    return true;
  }

  /**
   * Whether the element provides a datasource.
   *
   * @return Whether the element provides a datasource.
   */
  default boolean isDataSourceComponent() {
    // TODO might not need.
    return true;
  }

  /**
   * Attributes to pass to the request when the element is rendered.
   *
   * @return Attributes to pass to the request when the element is rendered.
   */
  @JsonIgnore
  default Map<String, String> getRequestAttributes() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("resourceType", getComponentResourceType());
    return attributes;
  }

  /**
   * Builds a synthetic resource for this element under the given parent path.
   *
   * @param resourceResolver ResourceResolver the synthetic resource is built with.
   * @param parentPath Path the synthetic resource is built under.
   * @return Synthetic resource for this element.
   */
  @JsonIgnore
  Resource toSyntheticResource(@Nonnull final ResourceResolver resourceResolver,
      @Nonnull final String parentPath);

  /**
   * Resource type the element renders as.
   *
   * @return Resource type the element renders as.
   */
  @Nonnull
  String getComponentResourceType();

  /**
   * Resource name the element is forced to use, if one was configured.
   *
   * @return Resource name the element is forced to use, if one was configured.
   */
  @Nullable
  String getForcedResourceName();

  /**
   * Service the element looks its variations up through.
   *
   * @return Service the element looks its variations up through.
   */
  @JsonIgnore
  @Nonnull
  ComponentVariationRetrievalService getComponentVariationRetrievalService();

  /**
   * Service the element looks its UiFramework views up through.
   *
   * @return Service the element looks its UiFramework views up through.
   */
  @JsonIgnore
  @Nonnull
  ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService();
}
