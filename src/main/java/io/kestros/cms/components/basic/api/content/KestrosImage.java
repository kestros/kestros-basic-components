package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface KestrosImage extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/image";

  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nullable
  String getImageTitle();

  @Nullable
  String getImagePath();

  @Nullable
  String getHref();

  @Nonnull
  String getAriaLabel();

  @Nullable
  String getAnchorTitle();

  @Nonnull
  AnchorTarget getTarget();

  @Nullable
  String getAltText();

  @Nullable
  String getCaption();
}