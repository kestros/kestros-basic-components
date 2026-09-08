package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public interface KestrosButtonGroup extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/button-group";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  default List<Resource> getButtons() {
    final List<KestrosButton> sourceButtons = getButtonsElements();
    final List<Resource> buttons = new ArrayList<>(sourceButtons.size());
    for (KestrosButton button : sourceButtons) {
      buttons.add(button.getResource());
    }
    return buttons;
  }

  @Nonnull
  List<KestrosButton> getButtonsElements();

  @Nonnull
  List<ComponentVariation> getButtonVariations();

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getButtonsElements());
  }
}