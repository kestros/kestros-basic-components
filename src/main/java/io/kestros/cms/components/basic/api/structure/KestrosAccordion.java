package io.kestros.cms.components.basic.api.structure;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;


/**
 * Kestros Accordion Element. List of items which can be expanded/collapsed to show/hide content.
 */
public interface KestrosAccordion extends KestrosBasicComponentElement {

  /**
   * Gets the list of accordion panel resources.
   *
   * @return List of accordion panel resources.
   */
  @Nonnull
  default List<Resource> getPanels() {
    List<Resource> panelResources = new ArrayList<>();
    for (KestrosAccordionPanel panel : getPanelElements()) {
      if (panel.isSynthetic()) {
        panelResources.add(panel.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        panelResources.add(panel.getResource());
      }
    }
    return panelResources;
  }

  /**
   * Gets the list of accordion panel elements.
   *
   * @return List of accordion panel elements.
   */
  @Nonnull
  List<KestrosAccordionPanel> getPanelElements();
}