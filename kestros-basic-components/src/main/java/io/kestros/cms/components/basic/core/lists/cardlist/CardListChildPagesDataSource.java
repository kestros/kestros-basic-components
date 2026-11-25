package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
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
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListChildPagesDataSource extends BaseContainerSlingModelDataSource implements
                                                                                    KestrosCardList {

  private static final Logger LOG = LoggerFactory.getLogger(CardListChildPagesDataSource.class);

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

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
    return getResource().getValueMap().get("readMoreText", String.class);
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    BaseContentPage root = getRootPage();
    if (root == null) {
      return new ArrayList<>();
    }
    CardListSupport.requireComponentPrerequisites(this);
    List<BaseContentPage> pages = new ArrayList<>(root.getChildPages());

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", false);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    pages = CardListSupport.sortPages(LOG, pages, sortBy, getResource().getPath());
    if (reverse) {
      Collections.reverse(pages);
    }

    // The limit counts cards that render, so a page dropped below does not cost the list a slot.
    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : pages) {
      if (limit > 0 && cards.size() >= limit) {
        break;
      }
      try {
        cards.add(
            new KestrosCardImpl(page,
                getReadMoreText(),
                this,
                "card",
//                getElementVariations("titleVariations", KestrosImage.RESOURCE_TYPE),
//                getLayout("title"),
//                getElementVariations("imageVariations", KestrosImage.RESOURCE_TYPE),
//                getLayout("image"),
//                getElementVariations("buttonGroupVariations", KestrosButtonGroup.RESOURCE_TYPE),
//                getLayout("buttonGroupLayout"),
//                getElementVariations("buttonVariations", KestrosButton.RESOURCE_TYPE),
//                getLayout("button"),
//                null,
                page.getName(),
                assetRetrievalService));
      } catch (Exception e) {
        // The prerequisites every card shares were checked above, so this failure belongs to this
        // page. Drop the page, keep the rest of the list, and say which page went and why.
        CardListSupport.logSkippedCard(LOG, page, getResource().getPath(), e);
      }
    }
    return new ArrayList<>(cards);
  }

}
