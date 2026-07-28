package io.kestros.cms.components.basic.core.structure.grid;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.structure.KestrosGrid;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosGrid} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class GridStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosGrid {

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return List.of();
  }
}
