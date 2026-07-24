package io.kestros.cms.components.basic.api.content;

/**
 * Core button element API.
 */
public interface KestrosButton extends KestrosLink {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/button";

  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Whether the link is disabled. Disabled links should not have an href.
   */
  boolean isDisabled();
}
