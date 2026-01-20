package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
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
