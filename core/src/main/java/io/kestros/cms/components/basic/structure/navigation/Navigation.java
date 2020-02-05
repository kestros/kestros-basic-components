package io.kestros.cms.components.basic.structure.navigation;

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getFirstAncestorOfType;
import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getParentResourceAsType;
import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getResourceAsType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.kestros.cms.foundation.content.BaseComponent;
import io.kestros.cms.foundation.content.pages.BaseContentPage;
import io.kestros.cms.foundation.content.sites.BaseSite;
import io.kestros.commons.structuredslingmodels.exceptions.InvalidResourceTypeException;
import io.kestros.commons.structuredslingmodels.exceptions.NoParentResourceException;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = {"kestros/commons/components/structure/navigation"})
public class Navigation extends BaseComponent {

  @JsonIgnoreProperties("components")
  public List<BaseContentPage> getNavigationPages() {
    try {
      return getRootPage().getChildPages();
    } catch (NoValidAncestorException e) {
      //      e.printStackTrace();
      // TODO LOG.
    }
    return Collections.emptyList();
  }


  protected BaseContentPage getRootPage() throws NoValidAncestorException {
    String rootPagePath = getProperty("rootPage", StringUtils.EMPTY);

    try {
      return getResourceAsType(rootPagePath, getResourceResolver(), BaseContentPage.class);
    } catch (InvalidResourceTypeException e) {
      try {
        return getResourceAsType(rootPagePath, getResourceResolver(), BaseSite.class);
      } catch (InvalidResourceTypeException ex) {
        //        ex.printStackTrace();
        // TODO LOG.
      } catch (ResourceNotFoundException ex) {
        //        ex.printStackTrace();
        // TODO LOG.
      }
    } catch (ResourceNotFoundException e) {
      //      e.printStackTrace();
      // TODO LOG.
    }

    if (!this.getContainingPage().getChildPages().isEmpty()) {
      return this.getContainingPage();
    } else {
      try {
        return getParentResourceAsType(this.getContainingPage(), BaseContentPage.class);
      } catch (InvalidResourceTypeException e) {
        //        e.printStackTrace();
        // TODO LOG.
      } catch (NoParentResourceException e) {
        //        e.printStackTrace();
        // TODO LOG.
      }
    }

    return getFirstAncestorOfType(this, BaseSite.class);
  }

}
