package io.kestros.cms.components.basic.core.structure.grid;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosGridImpl extends BaseContainerSyntheticResource implements KestrosGrid {

  private final List<KestrosContainer> columns;

  public KestrosGridImpl(
      @Nonnull List<KestrosContainer> columns,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.columns = new ArrayList<>(columns);
  }

  /**
   * Columns this grid holds.
   *
   * <p>The constructor took a list of columns and stored it, and nothing ever read it: the
   * interface default returned an empty list, so a synthetic grid rendered with no columns at all
   * however many it was built with.
   *
   * @return The columns handed to the constructor.
   */
  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(columns);
  }

}