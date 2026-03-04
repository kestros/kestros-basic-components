package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Synthetic table row implementation for programmatic table construction.
 */
public class KestrosTableRowImpl extends BaseContainerSyntheticResource implements
    KestrosTableRow {

  private final List<KestrosTableCell> cells;

  public KestrosTableRowImpl(@Nonnull List<KestrosTableCell> cells,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix,
      @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.cells = cells;
  }

  @Nonnull
  @Override
  public List<KestrosTableCell> getCellElements() {
    return new ArrayList<>(cells);
  }
}
