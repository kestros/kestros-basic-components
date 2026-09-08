package io.kestros.cms.components.basic.core.content.video;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A style rule rather than a defect: the detector fires on every class that"
        + " does not declare toString. The fields here are author content read from the"
        + " repository and are reached through the getters HTL calls, so a default rendering of"
        + " them is not something to add.")
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
