package io.kestros.cms.components.basic.structure.navigation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class NavigationComponentContext extends ComponentRequestContext {


  private static final Logger LOG = LoggerFactory.getLogger(NavigationComponentContext.class);

  /**
   * List of all pages to be shown in the navigation.
   *
   * @return List of all pages to be shown in the navigation.
   */
  @KestrosProperty(description = "Children of the root page.")
  @JsonIgnoreProperties({"components", "childPages"})
  public List<NavigationPage> getNavigationPages() {
    List<NavigationPage> navigationPages = new ArrayList<>();
    try {
      for (BaseContentPage childPage : getNavigationComponent().getRootPage().getChildPages()) {
        boolean active = getCurrentPage().getPath().equals(childPage.getPath());
        navigationPages.add(new NavigationPage(childPage.getTitle(), childPage.getDescription(),
            childPage.getPath() + ".html", active));
      }
    } catch (NoValidAncestorException e) {
      LOG.error("No valid ancestor found for navigation component.", e);
    }

    return navigationPages;
  }

  NavigationComponent getNavigationComponent() {
    return getResource().adaptTo(NavigationComponent.class);
  }
}
