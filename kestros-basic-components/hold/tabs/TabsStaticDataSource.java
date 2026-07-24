package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabs;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TabsStaticDataSource extends BaseSlingModelDataSource implements KestrosTabs {
  @Override
  public List<KestrosTab> getTabs() {
    List<KestrosTab> tabs = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosTab tab = childResource.adaptTo(TabStaticDataSource.class);
      if (tab != null) {
        tabs.add(tab);
      }
    }
    return new ArrayList<>(tabs);
  }

  @Nonnull
  @Override
  public List<Resource> getChildren() {
    return List.of();
  }
}
