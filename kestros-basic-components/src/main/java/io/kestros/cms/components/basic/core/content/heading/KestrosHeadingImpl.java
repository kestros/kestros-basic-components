package io.kestros.cms.components.basic.core.content.heading;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Programmatic {@link KestrosHeading}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosHeadingImpl extends BaseSyntheticResource implements KestrosHeading {
  private String text;
  private String headingType;

  /**
   * Constructs a heading impl.
   *
   * @param text Text.
   * @param headingType Heading type.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosHeadingImpl(@Nonnull String text, @Nonnull String headingType,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.text = text;
    this.headingType = headingType;
  }

  /**
   * Constructs a heading impl.
   *
   * @param resource Resource.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
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
