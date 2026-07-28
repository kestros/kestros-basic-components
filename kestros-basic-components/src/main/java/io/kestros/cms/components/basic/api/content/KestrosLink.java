package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * API for a basic link component.
 */
public interface KestrosLink extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/link";

  /**
   * Component resource type.
   *
   * @return Component resource type.
   */
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Visible link text. This should normally be present and meaningful.
   *
   * @return Visible link text.
   */
  @Nullable
  String getText();

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
  String getTitle();

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