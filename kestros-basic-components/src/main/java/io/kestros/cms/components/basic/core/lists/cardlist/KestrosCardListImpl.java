package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosCardListImpl extends BaseContainerSyntheticResource implements KestrosCardList {

  private List<KestrosCard> cards;

  public KestrosCardListImpl(
      @Nonnull List<KestrosCard> cards,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.cards = new ArrayList<>(cards);
  }


  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    return new ArrayList<>(cards);
  }
}
