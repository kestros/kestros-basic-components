package io.kestros.cms.components.basic.structure.navigation;

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getFirstAncestorOfType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.kestros.cms.foundation.content.pages.BaseContentPage;
import io.kestros.cms.foundation.content.sites.BaseSite;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = {"kestros/commons/components/structure/top-navigation"})
public class TopNavigation extends Navigation {

  @JsonIgnore
  @Override
  public BaseContentPage getRootPage() throws NoValidAncestorException {
    return getFirstAncestorOfType(this, BaseSite.class);
  }

}
