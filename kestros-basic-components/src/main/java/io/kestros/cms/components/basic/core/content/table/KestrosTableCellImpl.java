package io.kestros.cms.components.basic.core.content.table;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
/**
 * Programmatic (synthetic) {@link KestrosTableCell}, letting a datasource build table cells in code
 * rather than from authored child resources. Mirrors
 * {@link io.kestros.cms.components.basic.core.content.card.KestrosCardImpl}. A cell is either simple
 * text or a list of rendered content elements (e.g. a link or image).
 */
public class KestrosTableCellImpl extends BaseContainerSyntheticResource implements KestrosTableCell {

  private final String text;
  private final List<KestrosBasicComponentElement> cellContentElements;

  /**
   * Text-only cell.
   *
   * @param text                cell text.
   * @param dataSource          owning datasource.
   * @param resourcePrefix      resource prefix (for variation/layout resolution).
   * @param forcedResourceName  synthetic resource name (unique within the row).
   * @throws ComponentConfigurationException if the synthetic resource cannot be configured.
   */
  public KestrosTableCellImpl(@Nullable final String text,
      @Nonnull final BaseSlingModelDataSource dataSource, @Nonnull final String resourcePrefix,
      @Nullable final String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = text;
    this.cellContentElements = new ArrayList<>();
  }

  /**
   * Cell containing rendered content elements (e.g. a link or image).
   *
   * @param cellContentElements the elements rendered inside the cell.
   * @param dataSource          owning datasource.
   * @param resourcePrefix      resource prefix (for variation/layout resolution).
   * @param forcedResourceName  synthetic resource name (unique within the row).
   * @throws ComponentConfigurationException if the synthetic resource cannot be configured.
   */
  public KestrosTableCellImpl(@Nonnull final List<KestrosBasicComponentElement> cellContentElements,
      @Nonnull final BaseSlingModelDataSource dataSource, @Nonnull final String resourcePrefix,
      @Nullable final String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = null;
    this.cellContentElements = new ArrayList<>(cellContentElements);
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    return new ArrayList<>(cellContentElements);
  }
}
