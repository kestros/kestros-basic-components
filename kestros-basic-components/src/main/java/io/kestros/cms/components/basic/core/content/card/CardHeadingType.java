package io.kestros.cms.components.basic.core.content.card;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

/**
 * Resolves the heading level a card title renders at.
 *
 * <p>A card an author builds picks its own level; the list does not set it. Only the cards a list
 * builds itself, from pages or assets, take the list's level. When no level is set the title
 * still renders, at {@link #DEFAULT}.</p>
 */
final class CardHeadingType {

  /**
   * Level used when none is set.
   */
  static final String DEFAULT = "h2";

  private CardHeadingType() {
    // static utility.
  }

  /**
   * Heading level for a card an author built: its own, or the default. A hand-built card in a list
   * does not take the list's level; Danny, 2026-10-05: "if building an adhoc card list, you can
   * choose the header per card."
   *
   * @param valueResource resource carrying the card's own headingType, which may be a titleElement
   *     child rather than the card node itself.
   * @return an h1-h6 value, never null or blank.
   */
  @Nonnull
  static String resolve(@Nonnull Resource valueResource) {
    String cardHeadingType = getHeadingType(valueResource);
    return cardHeadingType != null ? cardHeadingType : DEFAULT;
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
