package io.kestros.cms.components.basic.core.structure.grid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Programmatic {@link KestrosGrid}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosGridImpl extends BaseContainerSyntheticResource implements KestrosGrid {

  private List<KestrosContainer> columns;

  /**
   * Constructs a grid impl.
   *
   * @param columns Columns.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosGridImpl(
      @Nonnull List<KestrosContainer> columns,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.columns = new ArrayList<>(columns);
  }

}