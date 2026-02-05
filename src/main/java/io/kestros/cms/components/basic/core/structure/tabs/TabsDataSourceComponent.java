package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabs;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;


public class TabsDataSourceComponent extends BaseDataSourceComponent<KestrosTabs>
    implements KestrosTabs {
  @Override
  public List<KestrosTab> getTabs() {
    return null;
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    return List.of();
  }
}
