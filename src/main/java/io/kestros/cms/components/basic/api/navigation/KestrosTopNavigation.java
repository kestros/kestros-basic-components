package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.content.KestrosImage;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface KestrosTopNavigation extends KestrosNavigation {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/top-navigation";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  KestrosImage getLogo();

}