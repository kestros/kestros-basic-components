package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Kestros Accordion Panel Element. Individual panel within an accordion structure.
 */
public interface KestrosAccordionPanel extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/structure/accordion-panel";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Gets the title of the accordion panel.
   *
   * @return Title of the accordion panel.
   */
  @Nullable
  String getTitle();

  /**
   * Indicates whether the accordion panel is expanded on load.
   *
   * @return True if the panel is expanded, false otherwise.
   */
  @Nonnull
  Boolean isExpanded();

  /**
   * Indicates whether the accordion panel is disabled.
   *
   * @return True if the panel is disabled, false otherwise.
   */
  @Nonnull
  Boolean isDisabled();

  /**
   * Gets the ARIA label for the accordion panel.
   *
   * @return ARIA label string, or null if not set.
   */
  @Nullable
  String getAriaLabel();
}