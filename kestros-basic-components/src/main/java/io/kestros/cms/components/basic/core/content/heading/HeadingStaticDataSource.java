package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class HeadingStaticDataSource extends BaseSlingModelDataSource implements KestrosHeading {

  /**
   * Heading level used when the author never picked one.
   *
   * <p>This is the option the heading dialog marks selected, and the element the HTL falls back
   * to, so a heading saved before the field existed keeps rendering as it always did.
   */
  private static final String DEFAULT_HEADING_TYPE = "h1";

  @Nullable
  @Override
  public String getHeadingText() {
    return getResource().getValueMap().get("headingText", String.class);
  }

  /**
   * Heading element to render this heading as.
   *
   * <p>Declared @Nonnull by the interface, and it used to return the property straight out of the
   * value map, so a heading with no headingType handed null to every caller of a method that
   * promised one.
   *
   * @return The configured heading level, or h1.
   */
  @Nonnull
  @Override
  public String getHeadingType() {
    return getResource().getValueMap().get("headingType", DEFAULT_HEADING_TYPE);
  }
}
