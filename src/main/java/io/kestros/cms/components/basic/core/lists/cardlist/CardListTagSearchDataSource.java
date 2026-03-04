package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
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

/**
 * Datasource that queries pages sharing tags with the current page.
 * Excludes the current page from results.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListTagSearchDataSource extends BaseContainerSlingModelDataSource implements
    KestrosCardList {

  @OSGiService
  private TagRetrievalService tagRetrievalService;

  @Nullable
  public String getReadMoreText() {
    return getResource().getValueMap().get("readMoreText", String.class);
  }

  List<BaseContentPage> getTaggedPages() {
    List<BaseContentPage> results = new ArrayList<>();
    if (tagRetrievalService == null) {
      return results;
    }

    BaseContentPage currentPage = getCurrentOrContainingPage();
    if (currentPage == null) {
      return results;
    }

    List<KestrosTag> currentTags = tagRetrievalService.getTagsOnResource(
        currentPage.getResource());
    if (currentTags.isEmpty()) {
      return results;
    }

    String pagesPath = getResource().getValueMap().get("pagesPath", String.class);
    Resource searchRoot;
    if (pagesPath != null) {
      searchRoot = getResourceResolver().getResource(pagesPath);
    } else {
      searchRoot = currentPage.getResource().getParent();
    }

    if (searchRoot == null) {
      return results;
    }

    Set<String> currentTagPaths = new HashSet<>();
    for (KestrosTag tag : currentTags) {
      currentTagPaths.add(tag.getPath());
    }

    Set<String> addedPaths = new HashSet<>();
    for (Resource childResource : searchRoot.getChildren()) {
      BaseContentPage childPage = childResource.adaptTo(BaseContentPage.class);
      if (childPage == null) {
        continue;
      }
      if (childPage.getPath().equals(currentPage.getPath())) {
        continue;
      }
      List<KestrosTag> childTags = tagRetrievalService.getTagsOnResource(childResource);
      for (KestrosTag childTag : childTags) {
        if (currentTagPaths.contains(childTag.getPath())
            && !addedPaths.contains(childPage.getPath())) {
          results.add(childPage);
          addedPaths.add(childPage.getPath());
          break;
        }
      }
    }

    return results;
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
        // Skip cards that fail to construct
      }
    }
    return new ArrayList<>(cards);
  }
}
