package io.kestros.cms.components.basic.core.navigation.breadcrumbs;

import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumb;
import io.kestros.cms.components.basic.api.navigation.KestrosBreadCrumbs;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.link.KestrosLinkImpl;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class BreadCrumbsPagePathDataSource extends BaseSlingModelDataSource
    implements KestrosBreadCrumbs {

  @Self
  @Optional
  private SlingHttpServletRequest slingHttpServletRequest;

  @Self
  @Optional
  private Resource resource;

  @Nonnull
  @Override
  public List<KestrosBreadCrumb> getLinks() {
    List<KestrosBreadCrumb> crumbs = new ArrayList<>();
    Boolean first = true;
    Boolean last = false;
    int index = 0;
    List<BaseContentPage> ancestorPages = getAncestorPages();
    for (BaseContentPage page : ancestorPages) {
      try {
        if (index == ancestorPages.size() - 1) {
          last = true;
        }
        KestrosLink link = new KestrosLinkImpl(page, this, "crumb", page.getName());

        KestrosBreadCrumb crumb = new KestrosBreadCrumbImpl(link, first, last, this, "crumb",
            page.getName());
        crumbs.add(crumb);
        first = false;
        index++;
      } catch (Exception e) {
        // Ignore exception and continue.
      }
    }
    return new ArrayList<>(crumbs);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    // todo this.
    return List.of();
  }

  List<BaseContentPage> getAncestorPages() {
    List<BaseContentPage> pages = new ArrayList<>();
    BaseContentPage page = getCurrentOrContainingPage();
    while (page != null) {
      pages.add(0, page);
      try {
        page = page.getParent();
      } catch (Exception e) {
        page = null;
      }
    }
    return pages;
  }
}
