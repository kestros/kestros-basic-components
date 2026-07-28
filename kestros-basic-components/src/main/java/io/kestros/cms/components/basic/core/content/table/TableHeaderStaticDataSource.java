package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosTableHeader} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableHeaderStaticDataSource extends BaseSlingModelDataSource
    implements KestrosTableHeader {

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
