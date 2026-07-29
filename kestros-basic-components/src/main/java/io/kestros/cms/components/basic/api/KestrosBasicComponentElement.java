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
 * Common API for every basic component element: its layout, the variations applied to it, and
 * how it turns itself into a synthetic resource for rendering.
 */
public interface KestrosBasicComponentElement {


  /**
   * Layout.
   *
   * @param propertyName Property name.
   * @return Layout.
   */
  @Nullable
  String getLayout(@Nonnull String propertyName);

  /**
   * Layout.
   *
   * @return Layout.
   */
  @Nonnull
  String getLayout();

  /**
   * Element variations.
   *
   * @param propertyName Property name.
   * @param componentType Component type.
   * @return Element variations.
   */
  @Nonnull
  List<ComponentVariation> getElementVariations(@Nonnull String propertyName,
      @Nonnull String componentType);


  /**
   * Id.
   *
   * @return Id.
   */
  @Nullable
  String getId();

  /**
   * Ui framework.
   *
   * @return Ui framework.
   * @throws InvalidThemeException If the invalid theme is not valid.
   * @throws ResourceNotFoundException If the resource not found is not valid.
   * @throws InvalidUiFrameworkException If the invalid ui framework is not valid.
   * @throws ThemeRetrievalException If the theme retrieval is not valid.
   * @throws UiFrameworkRetrievalException If the ui framework retrieval is not valid.
   * @throws ModelAdaptionException If the model adaption is not valid.
   */
  @JsonIgnore
  @Nullable
  UiFramework getUiFramework() throws InvalidThemeException, ResourceNotFoundException,
      InvalidUiFrameworkException, ThemeRetrievalException, UiFrameworkRetrievalException,
      ModelAdaptionException;

  /**
   * Resource.
   *
   * @return Resource.
   */
  @JsonIgnore
  @Nonnull
  Resource getResource();

  /**
   * Request.
   *
   * @return Request.
   */
  @JsonIgnore
  @Nullable
  SlingHttpServletRequest getRequest();

  /**
   * Resource resolver.
   *
   * @return Resource resolver.
   */
  @JsonIgnore
  @Nonnull
  ResourceResolver getResourceResolver();

  /**
   * Parent path.
   *
   * @return Parent path.
   */
  @JsonIgnore
  @Nullable
  String getParentPath();

  /**
   * Variations.
   *
   * @return Variations.
   */
  @Nonnull
  List<ComponentVariation> getVariations();

  /**
   * Inline variations.
   *
   * @return Inline variations.
   */
  @Nonnull
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
   * Path.
   *
   * @return Path.
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
   * Synthetic.
   *
   * @return Synthetic.
   */
  @Nonnull
  default Boolean isSynthetic() {
    return Boolean.TRUE;
  }

  /**
   * Data source component.
   *
   * @return Data source component.
   */
  default boolean isDataSourceComponent() {
    // TODO might not need.
    return true;
  }

  /**
   * Request attributes.
   *
   * @return Request attributes.
   */
  @JsonIgnore
  @Nonnull
  default Map<String, String> getRequestAttributes() {
    Map<String, String> attributes = new HashMap<>();
    attributes.put("resourceType", getComponentResourceType());
    return attributes;
  }

  /**
   * Synthetic resource.
   *
   * @param resourceResolver Resource resolver.
   * @param parentPath Parent path.
   * @return Synthetic resource.
   */
  @JsonIgnore
  @Nonnull
  Resource toSyntheticResource(@Nonnull final ResourceResolver resourceResolver,
      @Nonnull final String parentPath);

  /**
   * Component resource type.
   *
   * @return Component resource type.
   */
  @Nonnull
  String getComponentResourceType();

  /**
   * Forced resource name.
   *
   * @return Forced resource name.
   */
  @Nullable
  String getForcedResourceName();

  /**
   * Component variation retrieval service.
   *
   * @return Component variation retrieval service.
   */
  @JsonIgnore
  @Nonnull
  ComponentVariationRetrievalService getComponentVariationRetrievalService();

  /**
   * Component ui framework view retrieval service.
   *
   * @return Component ui framework view retrieval service.
   */
  @JsonIgnore
  @Nonnull
  ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService();
}