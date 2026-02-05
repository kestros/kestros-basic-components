package io.kestros.cms.components.basic.api.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;


/**
 * Kestros Accordion Element. List of items which can be expanded/collapsed to show/hide content.
 */
public interface KestrosAccordion extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/structure/accordion";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Gets the list of accordion panel resources.
   *
   * @return List of accordion panel resources.
   */
  @JsonIgnore
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

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getPanelElements());
  }
}