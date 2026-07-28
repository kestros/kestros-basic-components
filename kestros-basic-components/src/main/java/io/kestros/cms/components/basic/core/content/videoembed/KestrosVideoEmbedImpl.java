package io.kestros.cms.components.basic.core.content.videoembed;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic {@link KestrosVideoEmbed}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosVideoEmbedImpl extends BaseSyntheticResource implements KestrosVideoEmbed {

  private String videoEmbedCode;

  /**
   * Constructs a video embed impl.
   *
   * @param videoEmbedCode Video embed code.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
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
