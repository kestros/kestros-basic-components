package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import javax.annotation.Nonnull;

public interface KestrosBreadCrumb extends KestrosLink {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/breadcrumb";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  Boolean isFirstItem();
  Boolean isLastItem();
}
