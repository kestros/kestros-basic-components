package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.exceptions.ModelAdaptionException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class HeadingPageTitleDataSource extends HeadingStaticDataSource {
  private static final Logger LOG = LoggerFactory.getLogger(HeadingPageTitleDataSource.class);

  @Self
  @Optional
  private SlingHttpServletRequest request;

  /**
   * Page whose title this heading renders.
   *
   * <p>Every step here could already return null and none of them was guarded: the request is
   * injected as Optional, and both adaptTo calls return null for a resource with no adapter. The
   * override branch threw a RuntimeException out of a component render rather than falling back.
   *
   * @return The current or containing page, or null when none can be resolved.
   */
  @Nullable
  BaseContentPage getPage() {
    if (request == null) {
      return null;
    }
    try {
      if (isOverrideInheritedTitle()) {
        final ComponentRequestContext requestContext =
                request.adaptTo(ComponentRequestContext.class);
        return requestContext != null ? requestContext.getCurrentPage() : null;
      }
      final BaseComponent component = request.getResource().adaptTo(BaseComponent.class);
      return component != null ? component.getContainingPage() : null;
    } catch (ModelAdaptionException e) {
      LOG.warn("Unable to resolve the page behind a page-title heading; it renders no text.", e);
      return null;
    }
  }

  @Nullable
  @Override
  public String getHeadingText() {
    // getPage has always been able to return null, and this dereferenced it unguarded, so a
    // heading that could not resolve its page threw NullPointerException out of the render.
    final BaseContentPage currentPage = getPage();
    return currentPage != null ? currentPage.getDisplayTitle() : null;
  }

  @Nonnull
  public Boolean isOverrideInheritedTitle() {
    return getResource().getValueMap().get("overrideInheritedTitle", Boolean.FALSE);
  }
}
