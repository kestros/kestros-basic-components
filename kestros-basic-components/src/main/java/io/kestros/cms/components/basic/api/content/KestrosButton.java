package io.kestros.cms.components.basic.api.content;

import javax.annotation.Nonnull;
/**
 * Core button element API.
 */
public interface KestrosButton extends KestrosLink {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/button";

  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Whether the link is disabled. Disabled links should not have an href.
   */
  boolean isDisabled();
}
