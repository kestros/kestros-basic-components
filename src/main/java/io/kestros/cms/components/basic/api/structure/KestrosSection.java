package io.kestros.cms.components.basic.api.structure;

import javax.annotation.Nullable;

/**
 * Section container element. Generally these are styled groupings of elements, often with
 * background images or colors.
 */
public interface KestrosSection extends KestrosContainer {

  /**
   * Background image for the section.
   *
   * @return Background image for the section.
   */
  @Nullable
  String getBackgroundImage();
}