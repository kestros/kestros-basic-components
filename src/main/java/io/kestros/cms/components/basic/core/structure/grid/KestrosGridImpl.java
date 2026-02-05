package io.kestros.cms.components.basic.core.structure.grid;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosGridImpl extends BaseContainerSyntheticResource implements KestrosGrid {

  private List<KestrosContainer> columns;

  public KestrosGridImpl(
      @Nonnull List<KestrosContainer> columns,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.columns = columns;
  }

  @Override
  public List<KestrosContainer> getColumns() {
    return new ArrayList<>(columns);
  }
}