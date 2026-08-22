package io.kestros.cms.components.basic.core.lists.linklist;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.annotation.Nonnull;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.ContentPageSorter;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListChildPageDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {

  /**
   * The path the child pages are read from.
   *
   * <p>Empty rather than null when no path has been configured, so the @Nonnull this already
   * declared is true. getLinkElements treats empty the same way it used to treat null.</p>
   *
   * @return The configured path, or the empty string when none is set.
   */
  @Nonnull
  public String getRootPath() {
    return getResource().getValueMap().get("pagesPath", StringUtils.EMPTY);
  }

  @Nonnull
  public AnchorTarget getTarget() {
    return AnchorTarget.lookup(getResource());
  }

  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed ComponentElementRenderingException so the failure"
          + " stays identifiable, per the ruling on DataSourceComponent.")
  @Override
  @Nonnull
  public List<KestrosLink> getLinkElements() {
    List<BaseContentPage> pages = new ArrayList<>();
    String rootPath = getRootPath();
    if (StringUtils.isEmpty(rootPath)) {
      return new ArrayList<>();
    }
    Resource rootResource = getResourceResolver().getResource(rootPath);
    if (rootResource == null) {
      return new ArrayList<>();
    }
    for (Resource childResource : rootResource.getChildren()) {
      if ("jcr:content".equals(childResource.getName())) {
        continue;
      }
      BaseContentPage page = childResource.adaptTo(BaseContentPage.class);
      if (page != null) {
        pages.add(page);
      }
    }

    String sortBy = getResource().getValueMap().get("sortBy", "");
    boolean reverse = getResource().getValueMap().get("reverse", Boolean.FALSE);
    int limit = 0;
    try {
      limit = Integer.parseInt(getResource().getValueMap().get("limit", "0"));
    } catch (NumberFormatException e) {
      limit = 0;
    }

    ContentPageSorter.sort(pages, sortBy);

    if (reverse) {
      Collections.reverse(pages);
    }
    if (limit > 0 && pages.size() > limit) {
      pages = pages.subList(0, limit);
    }

    final List<BaseContentPage> sourceLinks = pages;
    final List<KestrosLink> links = new ArrayList<>(sourceLinks.size());
    for (BaseContentPage page : sourceLinks) {
      KestrosLink link = null;
      try {
        link = new KestrosLinkImpl(page.getDisplayTitle(),
            LinkUtils.getLink(page.getPath()),
            null,
            getTarget(),
            null,
            null,
            null,
            null,
            this,
            "link", null);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      links.add(link);
    }
    return links;
  }

}
