package io.kestros.cms.components.basic.core.content.video;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic {@link KestrosVideo}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosVideoImpl extends BaseSyntheticResource implements KestrosVideo {
  private String videoSource;
  private String fallbackText;

  /**
   * Constructs a video impl.
   *
   * @param videoSource Video source.
   * @param fallbackText Fallback text.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosVideoImpl(
      @Nonnull String videoSource,
      @Nonnull String fallbackText,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.videoSource = videoSource;
    this.fallbackText = fallbackText;
  }

  @Nullable
  @Override
  public String getVideoSource() {
    return videoSource;
  }

  @Nonnull
  @Override
  public String getFallbackText() {
    return fallbackText;
  }
}
