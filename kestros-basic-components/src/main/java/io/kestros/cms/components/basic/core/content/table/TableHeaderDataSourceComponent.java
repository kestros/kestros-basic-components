package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosTableHeader} handed to it by an upstream datasource, delegating every
 * value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TableHeaderDataSourceComponent extends BaseDataSourceComponent<KestrosTableHeader>
    implements KestrosTableHeader {

  @Nullable
  @Override
  public String getText() {
    return getComponentData().getText();
  }
}
