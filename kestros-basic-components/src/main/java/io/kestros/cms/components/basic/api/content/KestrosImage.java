package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * API for the image component element.
 */
public interface KestrosImage extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/image";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Image path.
   *
   * @return Image path.
   */
  @Nonnull
  String getImagePath();

  /**
   * Image title.
   *
   * @return Image title.
   */
  @Nullable
  String getImageTitle();

  /**
   * Caption.
   *
   * @return Caption.
   */
  @Nullable
  String getCaption();

  /**
   * Alt text.
   *
   * @return Alt text.
   */
  @Nonnull
  String getAltText();

  /**
   * Destination URL. Should be null if the link is not navigable.
   *
   * @return Destination URL.
   */
  @Nullable
  String getHref();

  /**
   * Target attribute (e.g. "_self", "_blank").
   *
   * @return Target attribute (e.g.
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
    if (getTarget() != null) {
      return getTarget().getTargetValue();
    }
    return AnchorTarget.SAME_WINDOW.getTargetValue();
  }

  /**
   * ARIA label. Used when visible text is missing or insufficient.
   *
   * @return ARIA label.
   */
  @Nullable
  String getAriaLabel();

  /**
   * Optional title attribute. Use sparingly – should add meaning, not duplicate text.
   *
   * @return Optional title attribute.
   */
  @Nullable
  String getAnchorTitle();

  /**
   * Relationship attribute (e.g. "noopener", "noreferrer", "nofollow").
   *
   * @return Relationship attribute (e.g.
   */
  @Nullable
  String getRel();

  /**
   * ARIA described-by ID reference.
   *
   * @return ARIA described-by ID reference.
   */
  @Nullable
  String getAriaDescribedBy();

  /**
   * Language of the link text, if different from the page language. Example: "fr", "de", "en-GB"
   *
   * @return Language of the link text, if different from the page language.
   */
  @Nullable
  String getLang();

}