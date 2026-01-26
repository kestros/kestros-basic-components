package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public abstract class BaseContainerSyntheticResource extends BaseSyntheticResource implements
        KestrosContainerElement {

  public BaseContainerSyntheticResource(
          @Nonnull ResourceResolver resourceResolver,
          @Nonnull UiFramework uiFramework,
          @Nonnull String parentPath,
          @Nonnull List<ComponentVariation> componentVariations,
          @Nonnull String layout, @Nullable String id, String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    List<Resource> children = new ArrayList<>();
    for (KestrosBasicComponentElement componentElement : getChildElements()) {
      children.add(componentElement.toSyntheticResource(getResourceResolver(), getPath()));
    }
    return children;
  }
}
