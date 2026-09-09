package io.kestros.cms.components.basic.core.lists.linklist;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
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

/**
 * Tag search datasource for the link list component. Finds pages matching configured
 * tags and renders them as link elements.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListTagSearchDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {

  private static final Logger LOG = LoggerFactory.getLogger(LinkListTagSearchDataSource.class);

  @OSGiService
  @org.apache.sling.models.annotations.Optional
  private TagRetrievalService tagRetrievalService;

  private BaseContentPage containingPage;

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
    Resource rootResource = getResourceResolver().getResource(rootPath);
    if (rootResource != null) {
      return rootResource.adaptTo(BaseContentPage.class);
    }
    return null;
  }

  @Nonnull
  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
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
  public List<KestrosLink> getLinkElements() {
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
          pages.sort(Comparator.comparing(LinkListTagSearchDataSource::getCreatedTime));
          break;
        case "lastModified":
          pages.sort(Comparator.comparing(LinkListTagSearchDataSource::getModifiedTime));
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

    List<KestrosLink> links = new ArrayList<>(pages.size());
    for (BaseContentPage page : pages) {
      try {
        links.add(new KestrosLinkImpl(page,
            this,
            "link",
            page.getName()));
      } catch (ComponentConfigurationException e) {
        LOG.warn("Unable to build a link for one tagged page; it is left out of the list.", e);
      }
    }
    return links;
  }
}
