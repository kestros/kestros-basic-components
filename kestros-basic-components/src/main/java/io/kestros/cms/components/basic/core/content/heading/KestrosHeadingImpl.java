package io.kestros.cms.components.basic.core.content.heading;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A style rule rather than a defect: the detector fires on every class that"
        + " does not declare toString. The fields here are author content read from the"
        + " repository and are reached through the getters HTL calls, so a default rendering of"
        + " them is not something to add.")
public class KestrosHeadingImpl extends BaseSyntheticResource implements KestrosHeading {
  private String text;
  private String headingType;

  public KestrosHeadingImpl(@Nonnull String text, @Nonnull String headingType,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.text = text;
    this.headingType = headingType;
  }

  public KestrosHeadingImpl(@Nonnull Resource resource,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = resource.getValueMap().get("headingText", String.class);
    this.headingType = resource.getValueMap().get("headingType", String.class);
    if (this.text == null || this.headingType == null) {
      throw new ComponentConfigurationException(String.format(
          "Unable to build a heading at %s: headingText and headingType are both required, and%n"
          + " headingText was %s, headingType was %s.",
          resource.getPath(), String.valueOf(this.text), String.valueOf(this.headingType)));
    }
  }

  @Nullable
  @Override
  public String getHeadingText() {
    return text;
  }

  @Nonnull
  @Override
  public String getHeadingType() {
    return headingType;
  }
}
