package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Base Sling Model Data Source for Kestros Container Elements.
 */
public abstract class BaseContainerSlingModelDataSource extends BaseSlingModelDataSource
    implements KestrosContainerElement {

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    List<Resource> children = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getChildElements()) {
      if (element.isSynthetic()) {
        children.add(element.toSyntheticResource(getResourceResolver(), getPath()));
      } else {
        children.add(element.getResource());
      }
    }
    return children;
  }

  @Nonnull
  public <T extends KestrosBasicComponentElement> List<T> getChildrenAsType(String resourceType, @Nonnull Class<T> clazz) {
    List<T> items = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if(childResource.isResourceType(resourceType) == false) {
        continue;
      }
      T item = childResource.adaptTo(clazz);
      if (item != null) {
        items.add(item);
      }
    }
    return new ArrayList<>(items);
  }

  @Nonnull
  public <T extends KestrosBasicComponentElement> List<T> getChildrenOfType(Class<T> clazz) {
    List<T> children = new java.util.ArrayList<>();
    for (KestrosBasicComponentElement element : getChildElements()) {
      if (clazz.isInstance(element)) {
        children.add(clazz.cast(element));
      }
    }
    return children;
  }

}
