package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

  /**
   * Page whose children are rendered as cards.
   *
   * @return The configured pagesPath as a page, else the containing page, or null when neither
   *     resolves.
   */
  @Nullable
  BaseContentPage getRootPage() {
    if (rootPage == null) {
      String pagesPath = getResource().getValueMap().get("pagesPath", String.class);
      if (pagesPath != null) {
        Resource pageResource = getResourceResolver().getResource(pagesPath);
        if (pageResource != null) {
          rootPage = pageResource.adaptTo(BaseContentPage.class);
        }
      } else {
        // adaptTo returns null for a resource with no BaseComponent adapter; dereferencing it
        // threw NullPointerException out of a method every caller treats as returning null.
        BaseComponent component = getResource().adaptTo(BaseComponent.class);
        if (component == null) {
          return null;
        }
        try {
          rootPage = component.getContainingPage();
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
    boolean reverse = getResource().getValueMap().get("reverse", Boolean.FALSE);
    int limit;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      switch (sortBy) {
        case "createdDate":
          pages.sort(Comparator.comparing(CardListChildPagesDataSource::getCreatedTime));
          break;
        case "lastModified":
          pages.sort(Comparator.comparing(CardListChildPagesDataSource::getModifiedTime));
          break;
        case "name":
          pages.sort(Comparator.comparing(BaseContentPage::getName));
          break;
        default:
          pages.sort(Comparator.comparing(BaseContentPage::getDisplayTitle));
          break;
      }
    }

    if (reverse) {
      Collections.reverse(pages);
    }
    if (limit > 0 && pages.size() > limit) {
      pages = pages.subList(0, limit);
    }

    List<KestrosCard> cards = new ArrayList<>(pages.size());
    for (BaseContentPage page : pages) {
      try {
        cards.add(
            new KestrosCardImpl(page,
                getReadMoreText(),
                this,
                "card",
                page.getName(),
                assetRetrievalService));
      } catch (Exception e) {
        // The prerequisites every card shares were checked above, so this failure belongs to this
        // page. Drop the page, keep the rest of the list, and say which page went and why.
        CardListSupport.logSkippedCard(LOG, page, getResource().getPath(), e);
      }
    }
    return cards;
  }

}
