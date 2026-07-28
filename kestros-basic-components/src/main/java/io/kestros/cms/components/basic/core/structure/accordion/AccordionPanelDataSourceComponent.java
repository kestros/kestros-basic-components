package io.kestros.cms.components.basic.core.structure.accordion;

import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosAccordionPanel} handed to it by an upstream datasource, delegating every
 * value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AccordionPanelDataSourceComponent
    extends BaseDataSourceComponent<KestrosAccordionPanel>
    implements KestrosAccordionPanel {

  @Nullable
  @Override
  public String getTitle() {
    return getComponentData().getTitle();
  }

  @Nonnull
  @Override
  public Boolean isExpanded() {
    return getComponentData().isExpanded();
  }

  @Nonnull
  @Override
  public Boolean isDisabled() {
    return getComponentData().isDisabled();
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getComponentData().getAriaLabel();
  }
}
