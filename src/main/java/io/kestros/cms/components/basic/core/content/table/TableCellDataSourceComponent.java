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

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableCellDataSourceComponent extends BaseContainerDataSourceComponent<KestrosTableCell>
    implements KestrosTableCell {

  @Nullable
  @Override
  public String getText() {
    return getComponentData().getText();
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    throw new UnsupportedOperationException(
        "TableCellDataSourceComponent does not support getChildElements().");
  }
}
