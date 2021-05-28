package io.kestros.cms.components.basic.content.map;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/map")
public class MapComponent extends BaseComponent {

  public String getAddress() {
    return getProperty("address", StringUtils.EMPTY);
  }

  public String getHeading() {
    return getProperty("heading", StringUtils.EMPTY);
  }

  public String getDescription() {
    return getProperty("description", StringUtils.EMPTY);
  }

}
