package io.kestros.cms.components.basic.core.structure.grid;

import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosGrid} handed to it by an upstream datasource, delegating every value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class GridDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosGrid>
    implements KestrosGrid {


}
