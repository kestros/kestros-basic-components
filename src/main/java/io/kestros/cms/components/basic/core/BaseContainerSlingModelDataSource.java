package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.core.content.BaseSlingModelDataSource;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Base Sling Model Data Source for Kestros Container Elements.
 */
public abstract class BaseContainerSlingModelDataSource extends BaseSlingModelDataSource implements
        KestrosContainerElement {
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

}
