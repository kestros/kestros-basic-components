package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class HeadingPageTitleDataSource extends HeadingStaticDataSource implements KestrosHeading {
  private static final Logger LOG = LoggerFactory.getLogger(HeadingPageTitleDataSource.class);

  @Self
  @Optional
  private SlingHttpServletRequest request;

  @Nullable
  BaseContentPage getPage() {
    try {
      if (isOverrideInheritedTitle()) {
        if (request == null) {
          throw new RuntimeException(
                  "SlingHttpServletRequest is required to override inherited title.");
        }
        ComponentRequestContext requestContext = request.adaptTo(ComponentRequestContext.class);
        if (requestContext == null) {
          LOG.warn("Unable to find text for page title component {}. The request does not adapt"
                  + " to a component request context.", getResource().getPath());
          return null;
        }
        return requestContext.getCurrentPage();
      } else {
        if (request == null) {
          LOG.warn("Unable to find text for page title component {}. No request is available.",
                  getResource().getPath());
          return null;
        }
        BaseComponent component = request.getResource().adaptTo(BaseComponent.class);
        if (component == null) {
          LOG.warn("Unable to find text for page title component {}. The resource does not adapt"
                  + " to a component.", getResource().getPath());
          return null;
        }
        return component.getContainingPage();
      }
    } catch (ModelAdaptionException e) {
      LOG.warn("Unable to find text for page title component {}. {}.", getResource().getPath(),
              e.getMessage());
      return null;
    }
  }

  @Nullable
  @Override
  public String getHeadingText() {
    BaseContentPage page = getPage();
    if (page == null) {
      return null;
    }
    return page.getDisplayTitle();
  }

  @Nonnull
  public Boolean isOverrideInheritedTitle() {
    return getResource().getValueMap().get("overrideInheritedTitle", Boolean.FALSE);
  }
}
