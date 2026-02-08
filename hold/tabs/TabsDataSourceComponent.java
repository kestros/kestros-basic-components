package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabs;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;


@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TabsDataSourceComponent extends BaseDataSourceComponent<KestrosTabs>
    implements KestrosTabs {
  @Override
  public List<KestrosTab> getTabs() {
    return getComponentData().getTabs();
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    return List.of();
  }
}
