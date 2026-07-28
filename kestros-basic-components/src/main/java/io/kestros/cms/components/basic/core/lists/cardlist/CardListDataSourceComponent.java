package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosCardList} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListDataSourceComponent extends
        BaseContainerDataSourceComponent<KestrosCardList> implements
        KestrosCardList {


  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    return getComponentData().getCardElements();
  }
}