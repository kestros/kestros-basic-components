package io.kestros.cms.components.basic.core.structure.grid;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A style rule rather than a defect: the detector fires on every class that"
        + " does not declare toString. The fields here are author content read from the"
        + " repository and are reached through the getters HTL calls, so a default rendering of"
        + " them is not something to add.")
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