package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableCellStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTableCell {

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    throw new UnsupportedOperationException(
        "TableCellStaticDataSource does not support getChildElements().");
  }
}
