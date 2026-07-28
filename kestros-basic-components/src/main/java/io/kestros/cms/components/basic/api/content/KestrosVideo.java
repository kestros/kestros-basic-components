package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * API for the video component element.
 */
public interface KestrosVideo extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/video";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Video source.
   *
   * @return Video source.
   */
  @Nullable
  String getVideoSource();

  /**
   * Fallback text.
   *
   * @return Fallback text.
   */
  @Nonnull
  String getFallbackText();
}