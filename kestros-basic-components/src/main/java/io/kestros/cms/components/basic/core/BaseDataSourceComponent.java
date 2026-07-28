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
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.DataSourceComponent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosBasicComponentElement} handed to it by an upstream datasource, delegating
 * every value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public abstract class BaseDataSourceComponent<T extends KestrosBasicComponentElement>
    extends DataSourceComponent<T> implements KestrosBasicComponentElement {

  private Resource syntheticResource;

  @Nullable
  @Override
  public String getId() {
    return getComponentData().getId();
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return getComponentData().getComponentVariationRetrievalService();
  }

  @Override
  @Nonnull
  public String getLayout() {
    return getComponentData().getLayout();
  }

  @Override
  @Nonnull
  public String getLayout(@Nonnull String propertyName) {
    return getComponentData().getLayout(propertyName);
  }

  @Override
  @Nonnull
  public List<ComponentVariation> getElementVariations(@Nonnull String propertyName,
      String componentType) {
    return getComponentData().getElementVariations(propertyName, componentType);
  }

  @Nullable
  @Override
  public String getForcedResourceName() {
    return getComponentData().getForcedResourceName();
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return getComponentData().getComponentUiFrameworkViewRetrievalService();
  }

  @Override
  @Nonnull
  public List<ComponentVariation> getVariations() {
    return getComponentData().getVariations();
  }

  @Override
  @Nonnull
  public Resource toSyntheticResource(@Nonnull ResourceResolver resourceResolver,
      @Nonnull String parentPath) {
    // TODO remove duplicate
    if (syntheticResource == null) {
      ResourceMetadata resourceMetadata = new ResourceMetadata();
      final String forcedName = getForcedResourceName();
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
            new HashMap<>((int) (children.size() / 0.75f) + 1);
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