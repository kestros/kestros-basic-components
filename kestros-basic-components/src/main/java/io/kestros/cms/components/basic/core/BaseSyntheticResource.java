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
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public abstract class BaseSyntheticResource extends BaseComponentElement {
  private final ResourceResolver resourceResolver;
  private final String parentPath;
  private final UiFramework uiFramework;
  private final List<ComponentVariation> componentVariations;
  private final String layout;
  private final String id;
  private Resource syntheticResource;
  private String resourceName;
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  private BaseSlingModelDataSource dataSource;

  protected BaseSyntheticResource(
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
      throw new ComponentConfigurationException(
          String.format("Unable to read the UiFramework for the %s element on %s. %s",
              resourcePrefix, this.parentPath, exception.getMessage()), exception);
    }
    this.componentVariations = dataSource.getElementVariations(resourcePrefix + "Variations",
        componentResourceTypeOf(this));
    this.layout = dataSource.getLayout(resourcePrefix);
    this.id = null;
    this.resourceName = forcedResourceName;
    // Every source below is annotated @Nonnull, so these guards look unreachable. They are not:
    // BaseSyntheticResourceTest builds this from a data source that returns null for each one in
    // turn, and the annotation is a claim about the contract, not a guarantee about a caller who
    // breaks it. Do not remove without deleting those tests, which is not allowed.
    requireConfigured(this.resourceResolver, resourcePrefix, "resource resolver");
    requireConfigured(this.parentPath, resourcePrefix, "parent path");
    requireConfigured(this.componentVariations, resourcePrefix, "component variation list");
    requireConfigured(this.layout, resourcePrefix, "layout");
    requireConfigured(this.uiFramework, resourcePrefix, "UiFramework");
    this.componentVariationRetrievalService = dataSource.getComponentVariationRetrievalService();
    this.componentUiFrameworkViewRetrievalService =
        dataSource.getComponentUiFrameworkViewRetrievalService();
  }

  /**
   * Fails construction when a data source handed back nothing for a property the element needs.
   *
   * <p>The message names the property. All three of these used to read "Missing required
   * property", so the three tests covering them could not tell which one had actually failed.
   *
   * @param value Value the data source returned.
   * @param resourcePrefix Prefix identifying which element is being built.
   * @param description Plain-English name of the property, used in the message.
   * @throws ComponentConfigurationException when the value is null.
   */
  private static void requireConfigured(@Nullable final Object value,
      @Nonnull final String resourcePrefix, @Nonnull final String description)
      throws ComponentConfigurationException {
    if (value == null) {
      throw new ComponentConfigurationException(
          String.format("Unable to build the %s element: its %s is missing.", resourcePrefix,
              description));
    }
  }

  /**
   * Resource type of the element being constructed.
   *
   * <p>Read through a static helper rather than called on {@code this} in the constructor body.
   * The method is a constant-returning default on each element's interface, but it is still
   * overridable, and calling one from a constructor reads a subclass that has not run its own
   * initialiser yet.
   *
   * @param element Element being constructed.
   * @return The element's component resource type.
   */
  @Nonnull
  private static String componentResourceTypeOf(
      @Nonnull final KestrosBasicComponentElement element) {
    return element.getComponentResourceType();
  }

  @Nullable
  @Override
  public String getId() {
    return this.id;
  }

  /**
   * Resolver this element reads through.
   *
   * <p>Read back off the data source rather than handed out of the field. It is the same object
   * either way - a ResourceResolver is a session handle and cannot be defensively copied - but
   * the resolver's owner is the data source, and this element is not the thing lending it out.
   *
   * @return The data source's resolver.
   */
  @Nonnull
  @Override
  public ResourceResolver getResourceResolver() {
    return dataSource.getResourceResolver();
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

  /**
   * Synthetic elements compare by identity, deliberately.
   *
   * <p>Comparing by value is not available here: the fields this class holds - parent path, layout,
   * forced resource name - are shared by every element a data source builds. Every card heading in
   * a list carries the prefix "title" and the name "titleElement" off one data source, so a
   * value-based equals would report headings with different text as the same heading.
   *
   * @param other Object to compare against.
   * @return True only when this is the same object.
   */
  @Override
  public boolean equals(@CheckForNull final Object other) {
    return this == other;
  }

  @Override
  public int hashCode() {
    return System.identityHashCode(this);
  }
}