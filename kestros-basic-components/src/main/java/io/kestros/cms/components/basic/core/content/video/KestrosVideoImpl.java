package io.kestros.cms.components.basic.core.content.video;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosVideoImpl extends BaseSyntheticResource implements KestrosVideo {
  private String videoSource;
  private String fallbackText;

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
