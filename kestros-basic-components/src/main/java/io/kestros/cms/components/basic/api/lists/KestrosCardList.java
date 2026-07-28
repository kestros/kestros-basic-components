package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * API for the card list component element.
 */
public interface KestrosCardList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/card-list";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Cards.
   *
   * @return Cards.
   */
  @Nonnull
  default List<Resource> getCards() {
    final List<KestrosCard> sourceCards = getCardElements();
    final List<Resource> cards = new ArrayList<>(sourceCards.size());
    for (KestrosCard card : sourceCards) {
      cards.add(card.getResource());
    }
    return cards;
  }

  /**
   * Card elements.
   *
   * @return Card elements.
   */
  @Nonnull
  List<KestrosCard> getCardElements();

  /**
   * Child elements.
   *
   * @return Child elements.
   */
  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getCardElements());
  }
}