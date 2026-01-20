package io.kestros.cms.components.basic.core.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.lists.KestrosLinkList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.KestrosLinkImpl;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = SlingHttpServletRequest.class)
public class LinkListChildPageDataSource extends BaseContainerSlingModelDataSource implements
        KestrosLinkList {

  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  public String getRootPath() {
    return getResource().getValueMap().get("pagesPath", String.class);
  }

  AnchorTarget getTarget() {
   return AnchorTarget.lookup(getResource());
  }

  @Override
  public List<KestrosLink> getLinks() {
    List<KestrosLink> links = new ArrayList<>();
    for (Resource childResource : getResourceResolver()
            .getResource(getRootPath()).getChildren()) {
      if (childResource.getName().equals("jcr:content")) {
        continue;
      }
      BaseContentPage page = childResource.adaptTo(BaseContentPage.class);
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
                getResourceResolver(),
                getUiFramework(),
                getResource().getPath(),
                KestrosBasicComponentElement.getAppliedVariations(
                        "linkVariations", getResource(),
                        "/libs/kestros/commons/components/content/link",
                        getUiFramework(),
                        componentVariationRetrievalService,
                        getComponentUiFrameworkViewRetrievalService()),
                KestrosBasicComponentElement.getLayout("linkLayout", getResource()),
                null,null);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      links.add(link);
    }
    return links;
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }
}