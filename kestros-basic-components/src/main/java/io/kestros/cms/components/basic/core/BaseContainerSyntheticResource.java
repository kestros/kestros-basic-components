package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

public abstract class BaseContainerSyntheticResource extends BaseSyntheticResource implements KestrosContainerElement {

  public BaseContainerSyntheticResource(
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    final List<KestrosBasicComponentElement> sourceChildren = getChildElements();
    final List<Resource> children = new ArrayList<>(sourceChildren.size());
    for (KestrosBasicComponentElement componentElement : sourceChildren) {
      children.add(componentElement.toSyntheticResource(getResourceResolver(), getPath()));
    }
    return children;
  }

}
