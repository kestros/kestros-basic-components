package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;

public interface KestrosRichText extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/richtext";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getText();
}
