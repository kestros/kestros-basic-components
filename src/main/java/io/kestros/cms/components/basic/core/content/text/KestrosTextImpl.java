package io.kestros.cms.components.basic.core.content.text;

import io.kestros.cms.components.basic.api.content.KestrosText;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosTextImpl extends BaseSyntheticResource implements KestrosText {
  private String text;

  public KestrosTextImpl(
      @Nonnull String text,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }
}
