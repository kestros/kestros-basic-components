package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosTableRow} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableRowStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTableRow {

  @Nonnull
  @Override
  public List<KestrosTableCell> getCellElements() {
    List<KestrosTableCell> cells = new ArrayList<>();
    for (Resource childResource : getResource().getChildren()) {
      KestrosTableCell cell = childResource.adaptTo(TableCellStaticDataSource.class);
      if (cell != null) {
        cells.add(cell);
      }
    }
    return new ArrayList<>(cells);
  }
}
