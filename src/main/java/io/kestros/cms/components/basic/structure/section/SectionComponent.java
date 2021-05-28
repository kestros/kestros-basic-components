package io.kestros.cms.components.basic.structure.section;

import io.kestros.cms.components.basic.structure.container.Container;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/structure/section")
public class SectionComponent extends Container {

  public String getHeading() {
    return getProperty("heading", StringUtils.EMPTY);
  }

  public String getDescription() {
    return getProperty("description", StringUtils.EMPTY);
  }
}
