/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * List of cards, built from the entries configured on it.
 */
public interface KestrosCardList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/card-list";

  @Override
  @Nonnull
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Resources backing the cards in the list.
   *
   * @return Resources backing the cards in the list.
   */
  @Nonnull
  default List<Resource> getCards() {
    List<KestrosCard> cardElements = getCardElements();
    List<Resource> cards = new ArrayList<>(cardElements.size());
    for (KestrosCard card : cardElements) {
      cards.add(card.getResource());
    }
    return cards;
  }

  /**
   * Cards the list holds.
   *
   * @return Cards the list holds.
   */
  @Nonnull
  List<KestrosCard> getCardElements();

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getCardElements());
  }
}
