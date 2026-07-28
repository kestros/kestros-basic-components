package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.api.table.KestrosTableRow;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosTableRow} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableRowDataSourceComponent extends BaseContainerDataSourceComponent<KestrosTableRow>
    implements KestrosTableRow {

  @Nonnull
  @Override
  public List<KestrosTableCell> getCellElements() {
    return getComponentData().getCellElements();
  }
}
