package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents an alert component that may provide an optional heading and text body.
 * <p>
 * Implementations expose the component's heading and text, and expose the Sling resource type used
 * to render the alert. Nullability of return values is indicated by the {@link Nullable} and
 * {@link Nonnull} annotations.
 */
public interface KestrosAlert extends KestrosBasicComponentElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/alert";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Returns the alert's heading text.
   *
   * @return the heading text, or {@code null} if no heading is configured
   */
  @Nullable
  String getHeading();

  /**
   * Returns the alert's main text content.
   *
   * @return the alert text, or {@code null} if no text is configured
   */
  @Nullable
  String getText();
}