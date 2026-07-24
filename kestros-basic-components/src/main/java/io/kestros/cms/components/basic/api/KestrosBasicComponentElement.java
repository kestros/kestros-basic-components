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

public interface KestrosBasicComponentElement {


  String getLayout(String propertyName);

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

  @Nullable
  String getId();

  @JsonIgnore
  UiFramework getUiFramework() throws InvalidThemeException, ResourceNotFoundException,
      InvalidUiFrameworkException, ThemeRetrievalException, UiFrameworkRetrievalException,
      ModelAdaptionException;

  @JsonIgnore
  Resource getResource();

  @JsonIgnore
  @Nullable
  SlingHttpServletRequest getRequest();

  @JsonIgnore
  ResourceResolver getResourceResolver();

  @JsonIgnore
  String getParentPath();

  List<ComponentVariation> getVariations();

  default String getInlineVariations() {
    StringJoiner joiner = new StringJoiner(" ");
    for (ComponentVariation variation : getVariations()) {
      if (variation.isInlineVariation()) {
        joiner.add(variation.getName());
      }
    }
    return joiner.toString();
  }

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

  default Boolean isSynthetic() {
    return true;
  }

  default boolean isDataSourceComponent() {
    // TODO might not need.
    return true;
  }

  @JsonIgnore
  default Map<String, String> getRequestAttributes() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("resourceType", getComponentResourceType());
    return attributes;
  }

  @JsonIgnore
  Resource toSyntheticResource(@Nonnull final ResourceResolver resourceResolver,
      @Nonnull final String parentPath);

  @Nonnull
  String getLayout();

  @Nonnull
  String getComponentResourceType();

  @Nullable
  String getForcedResourceName();

  @JsonIgnore
  @Nonnull
  ComponentVariationRetrievalService getComponentVariationRetrievalService();

  @JsonIgnore
  @Nonnull
  ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService();
}