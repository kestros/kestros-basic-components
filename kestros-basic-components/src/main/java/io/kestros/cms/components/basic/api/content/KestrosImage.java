package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface KestrosImage extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/image";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Path of the image this component renders.
   *
   * <p>Null when no image has been chosen. This was declared @Nonnull, and all three data sources
   * returned null anyway, so callers were being promised something the code never provided.
   *
   * @return Path of the image, or null when none is configured.
   */
  @Nullable
  String getImagePath();

  @Nullable
  String getImageTitle();

  @Nullable
  String getCaption();

  @Nonnull
  String getAltText();

  /**
   * Destination URL. Should be null if the link is not navigable.
   */
  @Nullable
  String getHref();

  /**
   * Target attribute (e.g. "_self", "_blank").
   */
  @Nonnull
  AnchorTarget getTarget();

  /**
   * Get the target as a string.
   *
   * @return Target attribute value.
   */
  @Nonnull
  default String getTargetAsString() {
    return getTarget().getTargetValue();
  }

  /**
   * ARIA label. Used when visible text is missing or insufficient.
   */
  @Nullable
  String getAriaLabel();

  /**
   * Optional title attribute. Use sparingly – should add meaning, not duplicate text.
   */
  @Nullable
  String getAnchorTitle();

  /**
   * Relationship attribute (e.g. "noopener", "noreferrer", "nofollow").
   */
  @Nullable
  String getRel();

  /**
   * ARIA described-by ID reference.
   */
  @Nullable
  String getAriaDescribedBy();

  /**
   * Language of the link text, if different from the page language. Example: "fr", "de", "en-GB"
   */
  @Nullable
  String getLang();

}