package io.kestros.cms.components.basic.core.structure.grid;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class GridDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosGrid>
    implements KestrosGrid {


  @Override
  public List<KestrosContainer> getColumns() {
    return getComponentData().getColumns();
  }
}
