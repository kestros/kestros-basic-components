package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.ResourceResolver;

public class KestrosCardImpl extends BaseContainerSyntheticResource implements KestrosCard {

  private String title;
  private String description;
  private String imagePath;
  private String layout;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;

  public KestrosCardImpl(BaseContentPage page, @Nullable String buttonText,
          @Nonnull ResourceResolver resourceResolver,
          @Nonnull UiFramework uiFramework,
          @Nonnull String parentPath,
          @Nonnull List<ComponentVariation> componentVariations,
          @Nonnull String layout,
          @Nonnull List<ComponentVariation> imageVariations,
          @Nonnull String imageLayout,
          @Nonnull List<ComponentVariation> buttonGroupVariations,
          @Nonnull String buttonGroupLayout,
          @Nonnull List<ComponentVariation> buttonVariations,
          @Nonnull String buttonLayout,
          @Nullable String id,
          @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
    this.title = page.getDisplayTitle();
    this.description = page.getDescription();
    try {
      this.image = new KestrosImageImpl(page.getImagePath(), null, null, null,
              null, null, null, AnchorTarget.SAME_WINDOW,
              resourceResolver, uiFramework, parentPath,
              imageVariations, imageLayout,
              id,
              forcedResourceName);
    } catch (final ComponentConfigurationException e) {
      this.image = null;
    }
    try {
      List<KestrosButton> buttons = Arrays.asList(
              new KestrosButtonImpl(buttonText, LinkUtils.getLink(page.getPath()), null,
                      AnchorTarget.SAME_WINDOW, null, null, null, null, false, resourceResolver,
                      uiFramework, parentPath,
                      buttonVariations,
                      buttonLayout,
                      id, forcedResourceName));
      this.buttonGroup = new KestrosButtonGroupImpl(buttons, buttonVariations, resourceResolver,
              uiFramework, parentPath,
              buttonGroupVariations,buttonGroupLayout,
              id, forcedResourceName);
    } catch (final ComponentConfigurationException e) {
      this.buttonGroup = null;
    }
  }

  public KestrosCardImpl(String title, String description, KestrosImage image,
          KestrosButtonGroup buttonGroup,
          @Nonnull ResourceResolver resourceResolver,
          @Nonnull UiFramework uiFramework,
          @Nonnull String parentPath, List<ComponentVariation> componentVariations,
          @Nonnull String layout,
          @Nullable String id,
          @Nullable String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
    this.title = title;
    this.description = description;
    this.image = image;
    this.buttonGroup = buttonGroup;
  }

  @Nullable
  @Override
  public String getTitle() {
    return title;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    return image;
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    return buttonGroup;
  }

}
