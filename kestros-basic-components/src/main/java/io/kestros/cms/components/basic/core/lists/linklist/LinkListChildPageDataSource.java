package io.kestros.cms.components.basic.core.lists.linklist;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.annotation.Nonnull;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@SuppressFBWarnings("FCBL_FIELD_COULD_BE_LOCAL")
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class LinkListChildPageDataSource extends BaseContainerSlingModelDataSource
    implements KestrosLinkList {

  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  @Nonnull
  public String getRootPath() {
    return getResource().getValueMap().get("pagesPath", String.class);
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
    if (rootPath == null) {
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

    if (!sortBy.isEmpty()) {
      switch (sortBy) {
        case "createdDate":
          pages.sort(Comparator.comparing(p -> {
            Calendar cal = p.getResource().getChild("jcr:content") != null
                ? p.getResource().getChild("jcr:content").getValueMap()
                    .get("jcr:created", Calendar.class) : null;
            return cal != null ? cal.getTimeInMillis() : 0L;
          }));
          break;
        case "lastModified":
          pages.sort(Comparator.comparing(p -> {
            Calendar cal = p.getResource().getChild("jcr:content") != null
                ? p.getResource().getChild("jcr:content").getValueMap()
                    .get("jcr:lastModified", Calendar.class) : null;
            return cal != null ? cal.getTimeInMillis() : 0L;
          }));
          break;
        default:
          pages.sort(Comparator.comparing(p -> {
            switch (sortBy) {
              case "name":
                return p.getName() != null ? p.getName() : "";
              default:
                return p.getDisplayTitle() != null ? p.getDisplayTitle() : p.getName();
            }
          }));
          break;
      }
    }

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
