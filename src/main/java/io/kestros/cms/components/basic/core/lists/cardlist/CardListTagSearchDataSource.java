package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
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
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListTagSearchDataSource extends BaseContainerSlingModelDataSource implements
        KestrosCardList {
  private BaseContentPage rootPage;

  BaseContentPage getPage() {
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

  @Nonnull
  @Override
  public List<KestrosCard> getCards() {
    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : getPage().getChildPages()) {
      try {
        cards.add(
                new KestrosCardImpl(page.getDisplayTitle(), page.getDisplayDescription(), null,
                        null,
                        getResourceResolver(), getUiFramework(), getPath(),
                        KestrosBasicComponentElement.getAppliedVariations("cardVariations",
                                getResource(),
                                "/libs/kestros/commons/components/content/card",
                                getUiFramework(),
                                getComponentVariationRetrievalService(),
                                getComponentUiFrameworkViewRetrievalService()),
                        KestrosBasicComponentElement.getLayout("cardLayout", getResource()),
                        null, null));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ArrayList<>(cards);
  }

}
