package io.kestros.cms.components.basic.core.structure.container;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosContainer} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ContainerDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosContainer>
    implements KestrosContainer {


  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return getComponentData().getChildElements();
  }
}
