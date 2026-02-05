package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabContent;
import io.kestros.cms.components.basic.api.structure.KestrosTabHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosTabImpl extends BaseSyntheticResource implements KestrosTab {
  private KestrosTabHeader tabHeader;
  private KestrosTabContent tabContent;

  public KestrosTabImpl(
      @Nonnull KestrosTabHeader tabHeader,
      @Nonnull KestrosTabContent tabContent,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix,
      @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.tabHeader = tabHeader;
    this.tabContent = tabContent;
  }


  @Override
  public KestrosTabHeader getTabHeader() {
    return tabHeader;
  }

  @Override
  public KestrosTabContent getTabContent() {
    return tabContent;
  }
}
