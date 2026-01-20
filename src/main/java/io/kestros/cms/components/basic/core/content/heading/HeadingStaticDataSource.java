package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.content.BaseSlingModelDataSource;
import io.kestros.cms.sitebuilding.api.models.ComponentRequestContext;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class HeadingStaticDataSource extends BaseSlingModelDataSource implements KestrosHeading {
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }

  @Override
  public String getHeadingType() {
    return getResource().getValueMap().get("headingLevel", String.class);
  }
}
