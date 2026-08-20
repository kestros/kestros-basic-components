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
import java.util.ArrayList;

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
   * The columns the grid was built with.
   *
   * <p>KestrosGrid inherits an empty default, so before this override a grid stored the columns it
   * was constructed with and then rendered none of them - getChildren() reads getChildElements()
   * and got back the default empty list. KestrosContainerImpl already returned its children this
   * way; the grid is the same kind of container and now behaves like one.</p>
   *
   * @return The columns, as a copy.
   */
  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(columns);
  }
}