package io.kestros.cms.components.basic.core.content.buttongroup;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public class KestrosButtonGroupImpl extends BaseContainerSyntheticResource implements
        KestrosButtonGroup {

  private List<KestrosButton> buttons;
  private List<ComponentVariation> buttonVariations;
  private String buttonLayout;

  public KestrosButtonGroupImpl(@Nonnull final List<KestrosButton> buttons,
          List<ComponentVariation> buttonVariations,
          ResourceResolver resourceResolver, UiFramework uiFramework,
          String parentPath,
          List<ComponentVariation> componentVariations, String layout, String id,String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id, forcedResourceName);
    this.buttons = buttons;
    this.buttonVariations = buttonVariations;
  }

  public KestrosButtonGroupImpl(@Nonnull Resource buttonResource,
          List<ComponentVariation> buttonVariations,
          ResourceResolver resourceResolver, UiFramework uiFramework,
          String parentPath,
          List<ComponentVariation> componentVariations, String layout, String id,
          String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
    this.buttonVariations = buttonVariations;
    this.buttons = new ArrayList<>();
    List<KestrosButton> buttons = new ArrayList<>();
    buttons.add(new KestrosButtonImpl(buttonResource, resourceResolver, uiFramework, parentPath,
            buttonVariations,
            KestrosBasicComponentElement.getLayout("buttonLayout", buttonResource), null,
            forcedResourceName));
    this.buttons = buttons;
  }

  @Nonnull
  @Override
  public List<KestrosButton> getButtons() {
    return new ArrayList<>(buttons);
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getButtonVariations() {
    return new ArrayList<>(buttonVariations);
  }
}
