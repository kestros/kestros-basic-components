package io.kestros.cms.components.basic.core.content.table;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
 * Programmatic (synthetic) {@link KestrosTableRow}, letting a datasource build table rows in code
 * rather than from authored child resources. Mirrors
 * {@link io.kestros.cms.components.basic.core.content.card.KestrosCardImpl}; the cells are usually
 * {@link KestrosTableCellImpl} instances built alongside it.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosTableRowImpl extends BaseContainerSyntheticResource implements KestrosTableRow {

  private final List<KestrosTableCell> cellElements;

  /**
   * Constructs a synthetic table row.
   *
   * @param cellElements        the row's cells, left-to-right.
   * @param dataSource          owning datasource.
   * @param resourcePrefix      resource prefix (for variation/layout resolution).
   * @param forcedResourceName  synthetic resource name (unique within the table).
   * @throws ComponentConfigurationException if the synthetic resource cannot be configured.
   */
  public KestrosTableRowImpl(@Nonnull final List<KestrosTableCell> cellElements,
      @Nonnull final BaseSlingModelDataSource dataSource, @Nonnull final String resourcePrefix,
      @Nullable final String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.cellElements = new ArrayList<>(cellElements);
  }

  @Nonnull
  @Override
  public List<KestrosTableCell> getCellElements() {
    return new ArrayList<>(cellElements);
  }
}
