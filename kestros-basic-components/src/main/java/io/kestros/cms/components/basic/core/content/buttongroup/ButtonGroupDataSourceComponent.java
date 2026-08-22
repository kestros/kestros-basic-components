package io.kestros.cms.components.basic.core.content.buttongroup;

import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ButtonGroupDataSourceComponent extends
        BaseContainerDataSourceComponent<KestrosButtonGroup> implements KestrosButtonGroup {

  private List<KestrosButton> buttons;
  private List<ComponentVariation> componentVariations;

  @Nonnull
  @Override
  public List<KestrosButton> getButtonsElements() {
    if (buttons == null) {
      buttons = getComponentData().getButtonsElements();
    }
    return new ArrayList<>(buttons);
  }

  @Override
  public List<ComponentVariation> getButtonVariations() {
    if (componentVariations == null) {
      componentVariations = getComponentData().getButtonVariations();
    }
    return new ArrayList<>(componentVariations);
  }

}
