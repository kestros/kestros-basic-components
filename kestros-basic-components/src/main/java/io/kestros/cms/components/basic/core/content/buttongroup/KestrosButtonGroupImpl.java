package io.kestros.cms.components.basic.core.content.buttongroup;

import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

public class KestrosButtonGroupImpl extends BaseContainerSyntheticResource implements
                                                                           KestrosButtonGroup {

  private List<KestrosButton> buttons;
  private final List<ComponentVariation> buttonVariations;

  public KestrosButtonGroupImpl(@Nonnull final List<KestrosButton> buttons,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.buttons = new ArrayList<>(buttons);
    this.buttonVariations = dataSource.getElementVariations("button", KestrosButton.RESOURCE_TYPE);
  }

  public KestrosButtonGroupImpl(@Nonnull Resource buttonResource,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.buttonVariations = dataSource.getElementVariations("button", KestrosButton.RESOURCE_TYPE);
    final List<KestrosButton> childButtons = new ArrayList<>();
    for (Resource childResource : buttonResource.getChildren()) {
      childButtons.add(new KestrosButtonImpl(childResource, dataSource,
          "button",
          childResource.getName()));
    }
    if (buttonResource.getValueMap().get("href", String.class) != null) {
      //In case of single button defined as button group.
      childButtons.add(new KestrosButtonImpl(buttonResource, dataSource,
          "button",
          "buttonElement"));
    }
    this.buttons = childButtons;
    if (this.buttons.isEmpty()) {
      throw new ComponentConfigurationException(String.format(
          "Button group on %s has no buttons; it needs at least one.", buttonResource.getPath()));
    }
  }

  @Nonnull
  @Override
  public List<KestrosButton> getButtonsElements() {
    return new ArrayList<>(buttons);
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getButtonVariations() {
    return new ArrayList<>(buttonVariations);
  }
}
