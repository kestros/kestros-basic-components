package io.kestros.cms.components.basic.core.lists.linklist;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.tagging.api.models.KestrosTag;
import io.kestros.cms.tagging.api.services.TagRetrievalService;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListTagSearchDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {

  @OSGiService
  @org.apache.sling.models.annotations.Optional
  private TagRetrievalService tagRetrievalService;

  private BaseContentPage containingPage;

  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
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

  BaseContentPage getRootPage() {
    BaseContentPage currentPage = getContainingPage();
    if (currentPage != null) {
      try {
        return currentPage.getParent();
      } catch (Exception e) {
        return currentPage;
      }
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

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", false);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    if (!sortBy.isEmpty()) {
      taggedPages.sort(Comparator.comparing(p -> {
        switch (sortBy) {
          case "name":
            return p.getName() != null ? p.getName() : "";
          default:
            return p.getDisplayTitle() != null ? p.getDisplayTitle() : p.getName();
        }
      }));
    }

    if (reverse) {
      Collections.reverse(taggedPages);
    }
    if (limit > 0 && taggedPages.size() > limit) {
      taggedPages = taggedPages.subList(0, limit);
    }

    return taggedPages;
  }

  @Nonnull
  @Override
  public List<KestrosLink> getLinkElements() {
    List<KestrosLink> links = new ArrayList<>();
    for (BaseContentPage page : getTaggedPages()) {
      try {
        links.add(
            new KestrosLinkImpl(page.getDisplayTitle(),
                LinkUtils.getLink(page.getPath()),
                null,
                getTarget(),
                null,
                null,
                null,
                null,
                this,
                "link",
                page.getName()));
      } catch (Exception e) {
        // Skip links that fail to construct
      }
    }
    return links;
  }
}
