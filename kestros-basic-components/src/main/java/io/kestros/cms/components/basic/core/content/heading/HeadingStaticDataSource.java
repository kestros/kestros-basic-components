package io.kestros.cms.components.basic.core.content.heading;

import javax.annotation.Nonnull;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class HeadingStaticDataSource extends BaseSlingModelDataSource implements KestrosHeading {
  @Override
  @Nonnull
  public String getHeadingText() {
    return getResource().getValueMap().get("headingText", String.class);
  }

  @Override
  @Nonnull
  public String getHeadingType() {
    return getResource().getValueMap().get("headingType", String.class);
  }
}
