package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
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
public class CardListChildPagesDataSource extends BaseContainerSlingModelDataSource implements
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

  @Nonnull
  @Override
  public List<KestrosCard> getCards() {
    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : getRootPage().getChildPages()) {
      try {
        cards.add(
                new KestrosCardImpl(page,
                        "Read More",
                        getResourceResolver(),
                        getUiFramework(),
                        getPath(),
                        KestrosBasicComponentElement.getAppliedVariations("cardVariations",
                                getResource(),
                                KestrosCard.RESOURCE_TYPE,
                                getUiFramework(),
                                getComponentVariationRetrievalService(),
                                getComponentUiFrameworkViewRetrievalService()),
                        KestrosBasicComponentElement.getLayout("cardLayout", getResource()),
                        KestrosBasicComponentElement.getAppliedVariations("imageVariations",
                                getResource(),
                                KestrosImage.RESOURCE_TYPE,
                                getUiFramework(),
                                getComponentVariationRetrievalService(),
                                getComponentUiFrameworkViewRetrievalService()),
                        KestrosBasicComponentElement.getLayout("imageLayout", getResource()),
                        KestrosBasicComponentElement.getAppliedVariations("buttonGroupVariations",
                                getResource(),
                                KestrosButtonGroup.RESOURCE_TYPE,
                                getUiFramework(),
                                getComponentVariationRetrievalService(),
                                getComponentUiFrameworkViewRetrievalService()),
                        KestrosBasicComponentElement.getLayout("buttonGroupLayout", getResource()),
                        KestrosBasicComponentElement.getAppliedVariations("buttonVariations",
                                getResource(),
                                KestrosButton.RESOURCE_TYPE,
                                getUiFramework(),
                                getComponentVariationRetrievalService(),
                                getComponentUiFrameworkViewRetrievalService()),
                        KestrosBasicComponentElement.getLayout("buttonLayout", getResource()),

                        null, null));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ArrayList<>(cards);
  }

}