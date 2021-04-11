package io.kestros.cms.components.basic.content.card;

import io.kestros.cms.componenttypes.api.exceptions.ComponentTypeRetrievalException;
import io.kestros.cms.componenttypes.api.exceptions.InvalidComponentTypeException;
import io.kestros.cms.componenttypes.api.exceptions.InvalidComponentUiFrameworkViewException;
import io.kestros.cms.componenttypes.api.models.ComponentType;
import io.kestros.cms.componenttypes.api.models.ComponentUiFrameworkView;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentTypeRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.cms.sitebuilding.api.models.ParentComponent;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidThemeException;
import io.kestros.cms.uiframeworks.api.exceptions.InvalidUiFrameworkException;
import io.kestros.cms.uiframeworks.api.exceptions.ThemeRetrievalException;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = SlingHttpServletRequest.class)
public class CardComponentContext extends ComponentRequestContext {

  @OSGiService
  @Optional
  private ComponentVariationRetrievalService componentVariationRetrievalService;

  @OSGiService
  @Optional
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  @OSGiService
  @Optional
  private ComponentTypeRetrievalService componentTypeRetrievalService;

  private List<ComponentVariation> appliedButtonVariations = null;

  private ComponentUiFrameworkView buttonView = null;

  public String getButtonInlineVariations() {
    final StringBuilder variationsStringBuilder = new StringBuilder();
    ParentComponent parentComponent = getRequest().getResource().adaptTo(ParentComponent.class);
    if (parentComponent != null) {
      for (final ComponentVariation variation : getAppliedButtonVariations()) {
        if (variation.isInlineVariation()) {
          variationsStringBuilder.append(variation.getName());
          variationsStringBuilder.append(" ");
        }
      }
    }
    if (variationsStringBuilder.length() > 1) {
      variationsStringBuilder.setLength(variationsStringBuilder.length() - 1);
    }
    return variationsStringBuilder.toString();
  }

  @Nonnull
  public List<ComponentVariation> getAppliedButtonVariations() {
    if (appliedButtonVariations != null) {
      return appliedButtonVariations;
    }

    final List<ComponentVariation> appliedVariations = new ArrayList<>();
    final List<String> appliedVariationNames = Arrays.asList(
        getComponent().getProperties().get("buttonVariations", new String[]{}));
    try {
      final ComponentUiFrameworkView uiFrameworkView = getButtonView();
      if (!appliedVariationNames.isEmpty()) {
        for (final String appliedVariation : appliedVariationNames) {
          for (final ComponentVariation variation :
              componentVariationRetrievalService.getComponentVariations(
              uiFrameworkView)) {
            if (variation.getPath().equals(appliedVariation) || variation.getName().equals(
                appliedVariation)) {
              appliedVariations.add(variation);
            }
          }
        }
      }

      if (appliedVariationNames.isEmpty()
          && !getComponent().getResource().getValueMap().containsKey("buttonVariations")) {
        for (ComponentVariation variation :
            componentVariationRetrievalService.getComponentVariations(
            uiFrameworkView)) {
          if (variation.isDefault()) {
            appliedVariations.add(variation);
          }
        }
      }
    } catch (final ModelAdaptionException exception) {
      //      LOG.warn("Unable to retrieve variations list for {}. {}", getComponent().getPath(),
      //          exception.getMessage());
    }

    //    LOG.trace("Finished retrieving applied variations for {}", getComponent().getPath());
    appliedButtonVariations = appliedVariations;
    return appliedButtonVariations;
  }

  private ComponentType getButtonComponentType() throws ComponentTypeRetrievalException {
    return componentTypeRetrievalService.getComponentType(
        "kestros/commons/components/content/button");
  }

  private ComponentUiFrameworkView getButtonView()
      throws InvalidThemeException, ThemeRetrievalException, ResourceNotFoundException,
             InvalidUiFrameworkException, ComponentTypeRetrievalException,
             InvalidComponentTypeException, InvalidComponentUiFrameworkViewException {
    return componentUiFrameworkViewRetrievalService.getComponentUiFrameworkViewWithFallback(
        getButtonComponentType(), getUiFramework());
  }
}
