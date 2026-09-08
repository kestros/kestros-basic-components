package io.kestros.cms.components.basic.core.structure.accordion;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosAccordionImpl extends BaseContainerSyntheticResource
    implements KestrosAccordion {

  private List<KestrosAccordionPanel> panels;

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
