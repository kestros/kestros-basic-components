package io.kestros.cms.components.basic.core.structure.accordion;

import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosAccordion} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AccordionDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosAccordion>
    implements KestrosAccordion {

  @Nonnull
  @Override
  public List<KestrosAccordionPanel> getPanelElements() {
    return getComponentData().getPanelElements();
  }
}
