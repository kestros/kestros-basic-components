package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosTableCell} handed to it by an upstream datasource, delegating every value
 * to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableCellDataSourceComponent extends BaseContainerDataSourceComponent<KestrosTableCell>
    implements KestrosTableCell {

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    return getComponentData().getCellContentElements();
  }

  @Override
  @Nullable
  public String getText() {
    KestrosTableCell data = getComponentData();
    return data == null ? null : data.getText();
  }
}
