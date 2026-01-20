package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.CardStaticDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListStaticDataSource extends BaseContainerSlingModelDataSource implements
        KestrosCardList {

  @Nonnull
  @Override
  public List<KestrosCard> getCards() {
    List<KestrosCard> cards = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosCard card = childResource.adaptTo(CardStaticDataSource.class);
      if (card != null) {
        cards.add(card);
      }
    }
    return new ArrayList<>(cards);
  }
}