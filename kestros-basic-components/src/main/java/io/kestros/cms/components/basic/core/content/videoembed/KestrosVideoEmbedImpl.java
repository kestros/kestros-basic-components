package io.kestros.cms.components.basic.core.content.videoembed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosVideoEmbedImpl extends BaseSyntheticResource implements KestrosVideoEmbed {

  private String videoEmbedCode;

  public KestrosVideoEmbedImpl(
      @Nonnull String videoEmbedCode,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.videoEmbedCode = videoEmbedCode;
  }

  @Nullable
  @Override
  public String getVideoEmbedCode() {
    return videoEmbedCode;
  }
}
