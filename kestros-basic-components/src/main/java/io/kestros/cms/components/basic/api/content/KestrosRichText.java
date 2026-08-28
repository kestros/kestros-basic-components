package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
