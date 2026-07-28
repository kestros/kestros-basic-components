package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableCellStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosTableCell {


  @Nonnull
  @Override
  public List<Resource> getCellContent() {
    List<Resource> cellContent = new java.util.ArrayList<>();
    for (Resource child : getResource().getChildren()) {
      cellContent.add(child);
    }
    return cellContent;
  }


  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    // Nothing to synthesize: a static cell's content is its child resources, which getCellContent
    // returns directly. The empty list satisfies the interface contract. The local that used to be
    // built here was never returned.
    return new ArrayList<>();
  }

  @Override
  @Nonnull
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
