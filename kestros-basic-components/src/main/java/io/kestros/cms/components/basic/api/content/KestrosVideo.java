package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface KestrosVideo extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/video";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getVideoSource();

  @Nonnull
  String getFallbackText();
}