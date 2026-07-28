package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * API for the heading component element.
 */
public interface KestrosHeading extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/heading";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Heading text.
   *
   * @return Heading text.
   */
  @Nullable
  String getHeadingText();

  /**
   * Heading type.
   *
   * @return Heading type.
   */
  @Nonnull
  String getHeadingType();
}