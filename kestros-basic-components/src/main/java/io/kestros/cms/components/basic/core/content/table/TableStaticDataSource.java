package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTable;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableStaticDataSource extends BaseContainerSlingModelDataSource
        implements KestrosTable {

  @Nonnull
  @Override
  public List<KestrosTableHeader> getHeaderElements() {
    List<KestrosTableHeader> headers = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (KestrosTableHeader.RESOURCE_TYPE.equals(childResource.getResourceType())) {
        KestrosTableHeader header = childResource.adaptTo(TableHeaderStaticDataSource.class);
        if (header != null) {
          headers.add(header);
        }
      }
    }
    return new ArrayList<>(headers);
  }

  @Nonnull
  @Override
  public List<KestrosTableRow> getRowElements() {
    List<KestrosTableRow> rows = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      if (KestrosTableRow.RESOURCE_TYPE.equals(childResource.getResourceType())) {
        KestrosTableRow row = childResource.adaptTo(TableRowStaticDataSource.class);
        if (row != null) {
          rows.add(row);
        }
      }
    }
    return new ArrayList<>(rows);
  }
}
