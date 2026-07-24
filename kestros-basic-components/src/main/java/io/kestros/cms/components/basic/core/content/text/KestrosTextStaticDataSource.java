package io.kestros.cms.components.basic.core.content.text;

import io.kestros.cms.components.basic.api.content.KestrosText;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class KestrosTextStaticDataSource extends BaseSlingModelDataSource implements KestrosText {
  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
