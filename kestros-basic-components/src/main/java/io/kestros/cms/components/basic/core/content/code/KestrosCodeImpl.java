package io.kestros.cms.components.basic.core.content.code;

import io.kestros.cms.components.basic.api.content.KestrosCode;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosCodeImpl extends BaseSyntheticResource implements KestrosCode {

  private final String code;

  public KestrosCodeImpl(@Nonnull String code,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.code = code;
  }


  @Nullable
  @Override
  public String getCode() {
    return code;
  }
}
