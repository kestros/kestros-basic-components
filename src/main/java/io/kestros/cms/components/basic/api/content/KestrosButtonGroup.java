package io.kestros.cms.components.basic.api.content;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.models.ComponentViewLayout;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public interface KestrosButtonGroup extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/button-group";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  List<KestrosButton> getButtons();

  @Nonnull
  List<ComponentVariation> getButtonVariations();

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getButtons());
  }
}