package io.kestros.cms.components.basic.api.content;

import javax.annotation.Nonnull;

/**
 * Core button element API.
 */
public interface KestrosButton extends KestrosLink {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/button";

  /**
   * Component resource type.
   *
   * @return Component resource type.
   */
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Whether the link is disabled. Disabled links should not have an href.
   *
   * @return Whether the link is disabled.
   */
  boolean isDisabled();
}
