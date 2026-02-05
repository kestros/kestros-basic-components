package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosTabHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosTabHeaderImpl extends BaseSyntheticResource implements KestrosTabHeader {
  private String title;
  private String targetContentId;
  private Boolean active;

  public KestrosTabHeaderImpl(
      @Nonnull String title,
      @Nonnull String targetContentId,
      @Nullable Boolean active,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix,
      @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.title = title;
    this.targetContentId = targetContentId;
    this.active = active;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String getTargetContentId() {
    return targetContentId;
  }

  @Override
  public Boolean isActive() {
    return active;
  }
}
