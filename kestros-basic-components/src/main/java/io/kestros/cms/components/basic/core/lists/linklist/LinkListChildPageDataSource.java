package io.kestros.cms.components.basic.core.lists.linklist;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListChildPageDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {

  private static final Logger LOG = LoggerFactory.getLogger(LinkListChildPageDataSource.class);

  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  /**
   * Path whose child pages are rendered as links.
   *
   * @return The configured pagesPath, or null when none is set.
   */
  @Nullable
  public String getRootPath() {
    return getResource().getValueMap().get("pagesPath", String.class);
  }

  @Nonnull
  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
  }

  @Nonnull
  @Override
  public List<KestrosLink> getLinkElements() {
    List<BaseContentPage> pages = new ArrayList<>();
    String rootPath = getRootPath();
    if (rootPath == null) {
      return new ArrayList<>();
    }
    Resource rootResource = getResourceResolver().getResource(rootPath);
    if (rootResource == null) {
      return new ArrayList<>();
    }
    for (Resource childResource : rootResource.getChildren()) {
      if (childResource.getName().equals("jcr:content")) {
        continue;
      }
      BaseContentPage page = childResource.adaptTo(BaseContentPage.class);
      if (page != null) {
        pages.add(page);
      }
    }

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
          pages.sort(Comparator.comparing(LinkListChildPageDataSource::getCreatedTime));
          break;
        case "lastModified":
          pages.sort(Comparator.comparing(LinkListChildPageDataSource::getModifiedTime));
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
        links.add(new KestrosLinkImpl(page.getDisplayTitle(),
            LinkUtils.getLink(page.getPath()),
            null,
            getTarget(),
            null,
            null,
            null,
            null,
            this,
            "link", null));
      } catch (ComponentConfigurationException e) {
        // One unbuildable page used to be rethrown as a RuntimeException, losing every link.
        LOG.warn("Unable to build a link for page {} in {}. It is left out of the list. {}",
            forLog(page.getPath()), forLog(getResource().getPath()), forLog(e.getMessage()), e);
      }
    }
    return links;
  }

}
