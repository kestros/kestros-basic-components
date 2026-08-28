package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

  /**
   * Page this data source sits on.
   *
   * @return The containing page, or null when the resource has no page ancestor.
   */
  @Nullable
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

  /**
   * Path the tag search is rooted at.
   *
   * @return The configured pagesPath, else the containing page's parent, or null when there is no
   *     containing page.
   */
  @Nullable
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

  /**
   * Page the tag search is rooted at.
   *
   * @return The root page, or null when no root path resolves to one.
   */
  @Nullable
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

  /**
   * Child pages of the root page carrying at least one of the configured tags.
   *
   * @return Matching pages, empty when nothing is configured or nothing matches.
   */
  @Nonnull
  List<BaseContentPage> getTaggedPages() {
    List<BaseContentPage> taggedPages = new ArrayList<>();
    if (tagRetrievalService == null) {
      return taggedPages;
    }

    String[] configuredTags = getConfiguredTags();
    if (configuredTags.length == 0) {
      return taggedPages;
    }

    Set<String> filterTagPaths = new HashSet<>(Arrays.asList(configuredTags));

    BaseContentPage currentPage = getContainingPage();
    BaseContentPage rootPage = getRootPage();
    if (rootPage == null) {
      return taggedPages;
    }

    for (BaseContentPage childPage : rootPage.getChildPages()) {
      if (currentPage != null && childPage.getPath().equals(currentPage.getPath())) {
        continue;
      }

      List<KestrosTag> childTags = tagRetrievalService.getTagsOnResource(
          childPage.getResource());
      for (KestrosTag childTag : childTags) {
        if (filterTagPaths.contains(childTag.getPath())) {
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
    List<BaseContentPage> pages = new ArrayList<>(getTaggedPages());

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
          pages.sort(Comparator.comparing(CardListTagSearchDataSource::getCreatedTime));
          break;
        case "lastModified":
          pages.sort(Comparator.comparing(CardListTagSearchDataSource::getModifiedTime));
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
      } catch (ComponentConfigurationException e) {
        LOG.warn("Unable to build a card for page {} in {}. It is left out of the list. {}",
            forLog(page.getPath()), forLog(getResource().getPath()), forLog(e.getMessage()), e);
      }
    }
    return cards;
  }
}
