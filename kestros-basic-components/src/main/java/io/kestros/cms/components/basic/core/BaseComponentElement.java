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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.componenttypes.api.exceptions.ComponentViewRetrievalException;
import io.kestros.cms.componenttypes.api.models.ComponentUiFrameworkView;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.commonutils.jcr.JcrPropertyUtils;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base component element component element.
 */
public abstract class BaseComponentElement implements KestrosBasicComponentElement {

  private static final Logger LOG = LoggerFactory.getLogger(BaseComponentElement.class);
  private Resource syntheticResource;

  @Override
  @Nonnull
  public String getLayout(@Nonnull String propertyName) {
    return getResource().getValueMap().get(propertyName + "Layout", "default");
  }

  @Override
  @Nonnull
  public List<ComponentVariation> getElementVariations(@Nonnull String propertyName,
      String componentType) {

    BaseComponent component = getResource().adaptTo(BaseComponent.class);
    final List<ComponentVariation> appliedVariations = new ArrayList<>();
    Object propertyValue = getResource().getValueMap().get(propertyName);
    // if list of maps
    final List<String> appliedVariationNames;
    if (propertyValue instanceof List && !((List<?>) propertyValue).isEmpty()
        && ((List<?>) propertyValue).get(0) instanceof Map) {
      // TODO checking the map here is a bit hacky, but not sure of a better way.
      final List<Map<String, Object>> variationMaps = (List<Map<String, Object>>) propertyValue;
      appliedVariationNames = new ArrayList<>(variationMaps.size());
      for (Map<String, Object> variationMap : variationMaps) {
        appliedVariationNames.add((String) variationMap.get("path"));
      }
    } else {
      appliedVariationNames = JcrPropertyUtils.getStringListOrEmptyList(
          getResource(),
          propertyName);
    }


    try {

      final ComponentUiFrameworkView uiFrameworkView
          = getComponentUiFrameworkViewRetrievalService().getResolvedComponentUiFrameworkView(
          componentType, getUiFramework(), component.getResourceResolver());
      List<ComponentVariation> variations
          = getComponentVariationRetrievalService().getComponentVariations(uiFrameworkView);
      if (!appliedVariationNames.isEmpty()) {
        for (final String appliedVariation : appliedVariationNames) {
          for (final ComponentVariation variation : variations) {
            if (variation.getPath().equals(appliedVariation) || variation.getName()
                .equals(appliedVariation)) {
              appliedVariations.add(variation);
            }
          }
        }
      }

      if (appliedVariationNames.isEmpty() && !getResource().getValueMap()
          .containsKey(propertyName)) {
        for (ComponentVariation variation : variations) {
          if (variation.isDefault()) {
            appliedVariations.add(variation);
          }
        }
      }
    } catch (final ModelAdaptionException | ComponentViewRetrievalException exception) {
      // A component whose variations cannot be resolved renders unstyled. That is the right
      // outcome -- it is better than failing the page -- but it used to happen silently, which
      // made an unstyled element impossible to tell apart from one with no variations applied.
      LOG.debug("Unable to resolve variations for {} on {}: {}",
          String.valueOf(propertyName).replaceAll("[\r\n]", ""),
          String.valueOf(getResource().getPath()).replaceAll("[\r\n]", ""),
          String.valueOf(exception.getMessage()).replaceAll("[\r\n]", ""));
    }
    return new ArrayList<>(appliedVariations);
  }

  @Override
  @Nonnull
  public Resource toSyntheticResource(@Nonnull ResourceResolver resourceResolver,
      @Nonnull String parentPath) {
    if (syntheticResource == null) {
      ResourceMetadata resourceMetadata = new ResourceMetadata();
      final String forcedName = this.getForcedResourceName();
      String name = "child-" + java.util.UUID.randomUUID();
      if (forcedName != null && !forcedName.isEmpty()) {
        name = forcedName;
      }
      String path = parentPath + "/" + name;
      if (!path.startsWith("/synthetics")) {
        path = "/synthetics" + path;
      }
      resourceMetadata.setResolutionPath(path);
      resourceMetadata.setModificationTime(System.currentTimeMillis());
      final Map<String, String> parameters = new HashMap<>(0);
      resourceMetadata.setParameterMap(parameters);
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> props = objectMapper.convertValue(this, Map.class);
      props.put("sling:resourceType", getComponentResourceType());
      props.put("jcr:primaryType", "nt:unstructured");
      syntheticResource = new PropertyBackedSyntheticResource(resourceResolver,
          resourceMetadata, getComponentResourceType(), props);
      if (this instanceof KestrosContainerElement) {
        final KestrosContainerElement container = (KestrosContainerElement) this;
        final List<KestrosBasicComponentElement> children = container.getChildElements();
        final Map<String, Resource> childResources =
            new java.util.LinkedHashMap<>((int) (children.size() / 0.75f) + 1);
        for (final KestrosBasicComponentElement child : children) {
          Resource childSyntheticResource = child.toSyntheticResource(resourceResolver,
              syntheticResource.getPath());
          childResources.put(childSyntheticResource.getName(), childSyntheticResource);
        }
        syntheticResource = new SyntheticResourceWrapper(syntheticResource, childResources);

      }
    }

    return syntheticResource;
  }
}
