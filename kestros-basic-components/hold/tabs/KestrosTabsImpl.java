package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabs;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosTabsImpl extends BaseContainerSyntheticResource implements KestrosTabs {

  private List<KestrosTab> tabs;

  public KestrosTabsImpl(
      @Nonnull List<KestrosTab> tabs,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix,
      String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.tabs = tabs;
  }


  @Override
  public List<KestrosTab> getTabs() {
    return new ArrayList<>(tabs);
  }
}
