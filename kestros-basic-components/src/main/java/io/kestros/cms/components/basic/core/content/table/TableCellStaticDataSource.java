package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableCellStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTableCell {


  @Nonnull
  @Override
  public List<Resource> getCellContent() {
    List<Resource> cellContent = new ArrayList<>();
    for (Resource child : getResource().getChildren()) {
      cellContent.add(child);
    }
    return cellContent;
  }


  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    // A static cell reads its content off child resources, so it never synthesizes elements. The
    // interface still has to be answered, and an empty list is the honest answer.
    return new ArrayList<>();
  }

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
