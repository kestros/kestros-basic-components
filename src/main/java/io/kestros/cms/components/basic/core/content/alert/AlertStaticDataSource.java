package io.kestros.cms.components.basic.core.content.alert;

import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.core.content.BaseSlingModelDataSource;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Sling Model Data Source for Alert component.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AlertStaticDataSource extends BaseSlingModelDataSource implements KestrosAlert {

  @Nullable
  @Override
  public String getHeading() {
    return getResource().getValueMap().get("heading", String.class);
  }

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}