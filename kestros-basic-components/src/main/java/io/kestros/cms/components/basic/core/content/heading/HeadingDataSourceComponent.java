package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosHeading} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class HeadingDataSourceComponent extends BaseDataSourceComponent<KestrosHeading> implements
        KestrosHeading {
  @Override
  @Nonnull
  public String getHeadingText() {
    return getComponentData().getHeadingText();
  }

  @Override
  @Nonnull
  public String getHeadingType() {
    return getComponentData().getHeadingType();
  }
}
