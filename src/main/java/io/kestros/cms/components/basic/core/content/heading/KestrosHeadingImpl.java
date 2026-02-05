package io.kestros.cms.components.basic.core.content.heading;

import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

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
    this.headingType = resource.getValueMap().get("headingLevel", String.class);
    if (this.text == null || this.headingType == null) {
      throw new ComponentConfigurationException("Missing required property");
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
