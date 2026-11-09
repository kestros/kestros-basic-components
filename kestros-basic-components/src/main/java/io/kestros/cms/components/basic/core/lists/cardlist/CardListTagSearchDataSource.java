package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListTagSearchDataSource extends BaseContainerSlingModelDataSource implements
                                                                                   KestrosCardList {

  private static final Logger LOG = LoggerFactory.getLogger(CardListTagSearchDataSource.class);

  @OSGiService
  @org.apache.sling.models.annotations.Optional
  private TagRetrievalService tagRetrievalService;

  @OSGiService
  @org.apache.sling.models.annotations.Optional
  private AssetRetrievalService assetRetrievalService;

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

  @Nonnull
  String[] getConfiguredTags() {
    String[] tags = getResource().getValueMap().get("tags", String[].class);
    if (tags == null) {
      return new String[0];
    }
    return tags;
  }

  String getRootPath() {
    String pagesPath = getResource().getValueMap().get("pagesPath", String.class);
    if (pagesPath != null) {
      return pagesPath;
    }
    BaseContentPage page = getContainingPage();
    if (page != null) {
      try {
        return page.getParent().getPath();
      } catch (Exception e) {
        return page.getPath();
      }
    }
    return null;
  }

  BaseContentPage getRootPage() {
    String rootPath = getRootPath();
    if (rootPath == null) {
      return null;
    }
    Resource rootResource = getResource().getResourceResolver().getResource(rootPath);
    if (rootResource != null) {
      return rootResource.adaptTo(BaseContentPage.class);
    }
    return null;
  }

  List<BaseContentPage> getTaggedPages() {
    List<BaseContentPage> taggedPages = new ArrayList<>();
    if (tagRetrievalService == null) {
      return taggedPages;
    }

    String[] configuredTags = getConfiguredTags();
    if (configuredTags.length == 0) {
      return taggedPages;
    }

    Set<String> filterTagPaths = new HashSet<>();
    for (String tagPath : configuredTags) {
      filterTagPaths.add(tagPath);
    }

    BaseContentPage currentPage = getContainingPage();
    BaseContentPage rootPage = getRootPage();
    if (rootPage == null) {
      return taggedPages;
    }
    // The component's own page and the root's children describe the component, not one page, so
    // a failure reading either still reaches the caller.
    String currentPath = currentPage != null ? currentPage.getPath() : null;

    for (BaseContentPage childPage : rootPage.getChildPages()) {
      try {
        if (isTaggedSibling(childPage, currentPath, filterTagPaths)) {
          taggedPages.add(childPage);
        }
      } catch (RuntimeException e) {
        CardListSupport.logSkippedCard(LOG, childPage, getResource().getPath(), e);
      }
    }

    return taggedPages;
  }

  private boolean isTaggedSibling(@Nonnull BaseContentPage childPage,
      @Nullable String currentPath, @Nonnull Set<String> filterTagPaths) {
    if (childPage.getPath().equals(currentPath)) {
      return false;
    }
    for (KestrosTag childTag : tagRetrievalService.getTagsOnResource(childPage.getResource())) {
      if (filterTagPaths.contains(childTag.getPath())) {
        return true;
      }
    }
    return false;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCardElements() {
    CardListSupport.requireComponentPrerequisites(this);
    List<BaseContentPage> pages = new ArrayList<>(getTaggedPages());

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
                page.getName(),
                assetRetrievalService));
      } catch (Exception e) {
        // The prerequisites every card shares were checked above, so this failure belongs to this
        // page. Skipping in silence is what hid this for months; say which page went and why.
        CardListSupport.logSkippedCard(LOG, page, getResource().getPath(), e);
      }
    }
    return new ArrayList<>(cards);
  }
}
