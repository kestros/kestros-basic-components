package io.kestros.cms.components.basic.content.pagetitle;

import io.kestros.cms.foundation.content.pages.BaseContentPage;
import io.kestros.commons.structuredslingmodels.BaseSlingRequest;
import io.kestros.commons.structuredslingmodels.exceptions.InvalidResourceTypeException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

/**
 * Provides the title of the requested page the the PageTitle component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PageTitleContext extends BaseSlingRequest {

  /**
   * Requested page's title.
   *
   * @return Requested page's title.
   */
  public String getText() {
    try {
      return SlingModelUtils.getResourceAsType(getRequest().getRequestURI().split(".html")[0],
          getRequest().getResourceResolver(), BaseContentPage.class).getDisplayTitle();
    } catch (InvalidResourceTypeException e) {
      e.printStackTrace();
    } catch (ResourceNotFoundException e) {
      e.printStackTrace();
    }
    return StringUtils.EMPTY;
  }

}
