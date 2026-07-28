package io.kestros.cms.components.basic.core.content.heading;

import javax.annotation.Nonnull;
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
        if (request != null) {
          return request.adaptTo(ComponentRequestContext.class).getCurrentPage();
        } else {
          throw new RuntimeException(
                  "SlingHttpServletRequest is required to override inherited title.");
        }
      } else {
        return request.getResource().adaptTo(BaseComponent.class).getContainingPage();
      }
    } catch (ModelAdaptionException e) {
      LOG.warn("Unable to find text for page title component {}. {}.",
          String.valueOf(getResource().getPath()).replaceAll("[\r\n]", ""),
              e.getMessage());
      return null;
    }
  }

  @Nullable
  @Override
  public String getHeadingText() {
    return getPage().getDisplayTitle();
  }

  @Nonnull
  public Boolean isOverrideInheritedTitle() {
    return getResource().getValueMap().get("overrideInheritedTitle", Boolean.FALSE);
  }
}
