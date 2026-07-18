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

public abstract class BaseComponentElement implements KestrosBasicComponentElement {
  private Resource syntheticResource;

  @Override
  @Nonnull
  public String getLayout(@Nonnull String propertyName) {
    return getResource().getValueMap().get(propertyName + "Layout", "default");
  }

  @Override
  public List<ComponentVariation> getElementVariations(String propertyName,
      String componentType) {

    BaseComponent component = getResource().adaptTo(BaseComponent.class);
    final List<ComponentVariation> appliedVariations = new ArrayList<>();
    Object propertyValue = getResource().getValueMap().get(propertyName);
    // if list of maps
    final List<String> appliedVariationNames;
    if (propertyValue instanceof List && !((List<?>) propertyValue).isEmpty()
        && ((List<?>) propertyValue).get(0) instanceof Map) {
      // TODO checking the map here is a bit hacky, but not sure of a better way.
      List<Map<String, Object>> variationMaps = (List<Map<String, Object>>) propertyValue;
      appliedVariationNames = new ArrayList<>();
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
    } catch (final ModelAdaptionException exception) {
    } catch (final ComponentViewRetrievalException e) {

    }
    return new ArrayList<>(appliedVariations);
  }

  @Override
  public Resource toSyntheticResource(@Nonnull ResourceResolver resourceResolver,
      @Nonnull String parentPath) {
    if (syntheticResource == null) {
      ResourceMetadata resourceMetadata = new ResourceMetadata();
      String name = "child-" + java.util.UUID.randomUUID();
      if (this.getForcedResourceName() != null && !this.getForcedResourceName().isEmpty()) {
        name = this.getForcedResourceName();
      }
      String path = parentPath + "/" + name;
      if (!path.startsWith("/synthetics")) {
        path = "/synthetics" + path;
      }
      resourceMetadata.setResolutionPath(path);
      resourceMetadata.setModificationTime(System.currentTimeMillis());
      Map<String, String> parameters = new HashMap<>();
      resourceMetadata.setParameterMap(parameters);
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> props = objectMapper.convertValue(this, Map.class);
      props.put("sling:resourceType", getComponentResourceType());
      props.put("jcr:primaryType", "nt:unstructured");
      syntheticResource = new SyntheticResource(resourceResolver, resourceMetadata,
          getComponentResourceType()) {
        private final ValueMap valueMap = new ValueMapDecorator(props);

        @Override
        public ValueMap getValueMap() {
          return valueMap;
        }
      };
      if (this instanceof KestrosContainerElement) {
        KestrosContainerElement container = (KestrosContainerElement) this;
        Map<String, Resource> childResources = new java.util.LinkedHashMap<>();
        for (KestrosBasicComponentElement child : container.getChildElements()) {
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
