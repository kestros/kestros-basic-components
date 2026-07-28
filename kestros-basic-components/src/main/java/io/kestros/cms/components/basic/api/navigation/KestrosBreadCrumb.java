package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import javax.annotation.Nonnull;

/**
 * API for the bread crumb component element.
 */
public interface KestrosBreadCrumb extends KestrosLink {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/breadcrumb";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * First item.
   *
   * @return First item.
   */
  @Nonnull
  Boolean isFirstItem();

  /**
   * Last item.
   *
   * @return Last item.
   */
  @Nonnull
  Boolean isLastItem();
}
