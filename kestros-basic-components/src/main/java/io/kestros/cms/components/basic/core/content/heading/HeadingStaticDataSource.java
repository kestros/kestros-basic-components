package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;

@Model(adaptables = SlingHttpServletRequest.class)
public class HeadingStaticDataSource extends BaseSlingModelDataSource implements KestrosHeading {
  @Override
  @Nullable
  public String getHeadingText() {
    return getResource().getValueMap().get("headingText", String.class);
  }

  /**
   * The heading element to render.
   *
   * <p>Declared @Nonnull on KestrosHeading, but the property is absent until an author picks a
   * level, so this used to return null. "h1" is what the dialog preselects and what the HTL
   * renders when data-sly-element gets nothing, so the default matches what was already on the
   * page.</p>
   *
   * @return The heading element name.
   */
  @Override
  @Nonnull
  public String getHeadingType() {
    return getResource().getValueMap().get("headingType", "h1");
  }
}
