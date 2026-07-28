package io.kestros.cms.components.basic.core.structure.accordion;

import io.kestros.cms.components.basic.api.structure.KestrosAccordion;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosAccordion} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AccordionStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosAccordion {
  @Nonnull
  @Override
  public List<KestrosAccordionPanel> getPanelElements() {
    List<KestrosAccordionPanel> panels = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosAccordionPanel panel = childResource.adaptTo(AccordionPanelStaticDataSource.class);
      if (panel != null) {
        panels.add(panel);
      }
    }
    return new ArrayList<>(panels);
  }
}
