package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Datasource that queries child pages under a configurable presenters path
 * and returns presenter cards with name, title, headshot, and sessions link.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListPresentersDataSource extends BaseContainerSlingModelDataSource implements
    KestrosCardList {

  private BaseContentPage rootPage;

  BaseContentPage getRootPage() {
    if (rootPage == null) {
      String pagesPath = getResource().getValueMap().get("pagesPath", String.class);
      if (pagesPath != null) {
        Resource pageResource = getResourceResolver().getResource(pagesPath);
        if (pageResource != null) {
          try {
            rootPage = pageResource.adaptTo(BaseContentPage.class);
          } catch (Exception e) {
            return null;
          }
        }
      } else {
        try {
          rootPage = getResource().adaptTo(BaseComponent.class).getContainingPage();
        } catch (NoValidAncestorException e) {
          return null;
        }
      }
    }
    return rootPage;
  }

  @Nullable
  public String getReadMoreText() {
    String text = getResource().getValueMap().get("readMoreText", String.class);
    if (text == null) {
      return "View Sessions";
    }
    return text;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    List<KestrosCard> cards = new ArrayList<>();
    BaseContentPage root = getRootPage();
    if (root == null) {
      return cards;
    }
    for (BaseContentPage page : root.getChildPages()) {
      try {
        cards.add(
            new KestrosCardImpl(page,
                getReadMoreText(),
                this,
                "card",
                page.getName()));
      } catch (Exception e) {
        // Skip cards that fail to construct
      }
    }
    return new ArrayList<>(cards);
  }
}
