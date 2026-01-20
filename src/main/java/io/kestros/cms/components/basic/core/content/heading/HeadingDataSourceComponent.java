package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class HeadingDataSourceComponent extends BaseDataSourceComponent<KestrosHeading> implements
        KestrosHeading {
  @Override
  public String getText() {
    return getComponentData().getText();
  }

  @Override
  public String getHeadingType() {
    return getComponentData().getHeadingType();
  }
}
