package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListTagSearchDataSource extends BaseContainerSlingModelDataSource implements
                                                                                   KestrosCardList {

  @OSGiService
  @org.apache.sling.models.annotations.Optional
  private TagRetrievalService tagRetrievalService;

  private BaseContentPage containingPage;

  @Nullable
  public String getReadMoreText() {
    return getResource().getValueMap().get("readMoreText", String.class);
  }

  BaseContentPage getContainingPage() {
    if (containingPage == null) {
      try {
        BaseComponent component = getResource().adaptTo(BaseComponent.class);
        if (component != null) {
          containingPage = component.getContainingPage();
        }
      } catch (NoValidAncestorException e) {
        return null;
      }
    }
    return containingPage;
  }

  BaseContentPage getRootPage() {
    String pagesPath = getResource().getValueMap().get("pagesPath", String.class);
    if (pagesPath != null) {
      Resource pageResource = getResourceResolver().getResource(pagesPath);
      if (pageResource != null) {
        return pageResource.adaptTo(BaseContentPage.class);
      }
    }
    return getContainingPage();
  }

  List<BaseContentPage> getTaggedPages() {
    List<BaseContentPage> taggedPages = new ArrayList<>();
    if (tagRetrievalService == null) {
      return taggedPages;
    }

    BaseContentPage currentPage = getContainingPage();
    if (currentPage == null) {
      return taggedPages;
    }

    List<KestrosTag> currentPageTags = tagRetrievalService.getTagsOnResource(
        currentPage.getResource());
    if (currentPageTags.isEmpty()) {
      return taggedPages;
    }

    Set<String> currentTagPaths = new HashSet<>();
    for (KestrosTag tag : currentPageTags) {
      currentTagPaths.add(tag.getPath());
    }

    BaseContentPage rootPage = getRootPage();
    if (rootPage == null) {
      return taggedPages;
    }

    for (BaseContentPage childPage : rootPage.getChildPages()) {
      if (childPage.getPath().equals(currentPage.getPath())) {
        continue;
      }

      List<KestrosTag> childTags = tagRetrievalService.getTagsOnResource(
          childPage.getResource());
      for (KestrosTag childTag : childTags) {
        if (currentTagPaths.contains(childTag.getPath())) {
          taggedPages.add(childPage);
          break;
        }
      }
    }

    return taggedPages;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    List<KestrosCard> cards = new ArrayList<>();
    for (BaseContentPage page : getTaggedPages()) {
      try {
        cards.add(
            new KestrosCardImpl(page,
                getReadMoreText(),
                this,
                "card",
                page.getName()));
      } catch (Exception e) {
        // Skip cards that fail to construct — null-safe
      }
    }
    return new ArrayList<>(cards);
  }
}
