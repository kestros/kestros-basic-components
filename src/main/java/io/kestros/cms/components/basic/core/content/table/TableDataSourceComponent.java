package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableDataSourceComponent extends BaseContainerDataSourceComponent<KestrosTable>
    implements KestrosTable {

  @Nonnull
  @Override
  public List<KestrosTableHeader> getHeaderElements() {
    return getComponentData().getHeaderElements();
  }

  @Nonnull
  @Override
  public List<KestrosTableRow> getRowElements() {
    return getComponentData().getRowElements();
  }
}
