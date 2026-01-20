package io.kestros.cms.components.basic.core.content.buttongroup;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.button.ButtonStaticDataSource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.models.ComponentViewLayout;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ButtonGroupStaticDataSource extends BaseContainerSlingModelDataSource implements
        KestrosButtonGroup {

  @Nonnull
  @Override
  public List<KestrosButton> getButtons() {
    List<KestrosButton> buttons = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosButton button = childResource.adaptTo(ButtonStaticDataSource.class);
      if (button != null) {
        buttons.add(button);
      }
    }
    return new ArrayList<>(buttons);
  }


  public List<ComponentVariation> getButtonVariations() {
    try {
      return KestrosBasicComponentElement.getAppliedVariations("buttonVariations",
              getResource(), KestrosButton.RESOURCE_TYPE,
              getUiFramework(),
              getComponentVariationRetrievalService(),
              getComponentUiFrameworkViewRetrievalService());
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }
}