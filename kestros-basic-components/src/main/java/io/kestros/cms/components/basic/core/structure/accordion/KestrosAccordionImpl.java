package io.kestros.cms.components.basic.core.structure.accordion;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Programmatic {@link KestrosAccordion}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosAccordionImpl extends BaseContainerSyntheticResource
    implements KestrosAccordion {

  private List<KestrosAccordionPanel> panels;

  /**
   * Constructs an accordion impl.
   *
   * @param panels Panels.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosAccordionImpl(
      @Nonnull List<KestrosAccordionPanel> panels,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.panels = new ArrayList<>(panels);
  }


  @Nonnull
  @Override
  public List<KestrosAccordionPanel> getPanelElements() {
    return new ArrayList<>(panels);
  }
}
