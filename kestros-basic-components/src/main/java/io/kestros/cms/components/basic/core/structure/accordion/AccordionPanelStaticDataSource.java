package io.kestros.cms.components.basic.core.structure.accordion;

import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AccordionPanelStaticDataSource extends BaseSlingModelDataSource
    implements KestrosAccordionPanel {
  @Nullable
  @Override
  public String getTitle() {
    return getResource().getValueMap().get("panelTitle", String.class);
  }

  @Nonnull
  @Override
  public Boolean isExpanded() {
    return getResource().getValueMap().get("panelExpanded", Boolean.FALSE);
  }

  @Nonnull
  @Override
  public Boolean isDisabled() {
    return getResource().getValueMap().get("panelDisabled", Boolean.FALSE);
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getResource().getValueMap().get("panelAriaLabel", String.class);
  }
}