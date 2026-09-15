package io.kestros.cms.components.basic.core.content.card;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

/**
 * Resolves the heading level a card title renders at.
 *
 * <p>The level is authored on the card list and inherited by every card it renders. A card may
 * override it for itself. When neither is set the title still renders, at {@link #DEFAULT}.</p>
 */
final class CardHeadingType {

  /**
   * Level used when neither the card nor its list names one.
   */
  static final String DEFAULT = "h2";

  private CardHeadingType() {
    // static utility.
  }

  /**
   * Heading level for a card, resolved from the card, then its list, then the default.
   *
   * @param cardResource the card's own resource.
   * @return an h1-h6 value, never null or blank.
   */
  @Nonnull
  static String resolve(@Nonnull Resource cardResource) {
    return resolve(cardResource, cardResource);
  }

  /**
   * Heading level for a card whose heading properties live on a separate resource.
   *
   * @param valueResource resource carrying the card's own headingType, which may be a titleElement
   *     child rather than the card node itself.
   * @param cardResource the card's resource, whose parent is the list to inherit from.
   * @return an h1-h6 value, never null or blank.
   */
  @Nonnull
  static String resolve(@Nonnull Resource valueResource, @Nonnull Resource cardResource) {
    String cardHeadingType = getHeadingType(valueResource);
    if (cardHeadingType != null) {
      return cardHeadingType;
    }
    String listHeadingType = getHeadingType(cardResource.getParent());
    if (listHeadingType != null) {
      return listHeadingType;
    }
    return DEFAULT;
  }

  /**
   * Heading level for a card the list builds itself, from a page or an asset rather than from an
   * authored card node.
   *
   * <p>Such a card has no resource of its own, so there is nothing to override with: the level is
   * the list's, or the default.</p>
   *
   * @param listResource the card list's resource, or null when the list has none.
   * @return an h1-h6 value, never null or blank.
   */
  @Nonnull
  static String resolveFromList(@Nullable Resource listResource) {
    String listHeadingType = getHeadingType(listResource);
    if (listHeadingType != null) {
      return listHeadingType;
    }
    return DEFAULT;
  }

  @Nullable
  private static String getHeadingType(@Nullable Resource resource) {
    if (resource == null) {
      return null;
    }
    String headingType = resource.getValueMap().get("headingType", String.class);
    if (StringUtils.isBlank(headingType)) {
      return null;
    }
    return headingType;
  }
}
