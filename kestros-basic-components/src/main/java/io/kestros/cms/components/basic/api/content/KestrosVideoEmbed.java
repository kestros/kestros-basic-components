package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;

public interface KestrosVideoEmbed extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/video-embed";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getVideoEmbedCode();
}