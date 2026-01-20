package io.kestros.cms.components.basic.core.content.alert;

import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Data Source Component for Kestros Alert components.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class AlertDataSourceComponent extends BaseDataSourceComponent<KestrosAlert> implements
        KestrosAlert {

  @Nullable
  @Override
  public String getHeading() {
    return getComponentData().getHeading();
  }

  @Nullable
  @Override
  public String getText() {
    return getComponentData().getText();
  }
}