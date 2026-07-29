package io.kestros.cms.components.basic.core.content.code;

import io.kestros.cms.components.basic.api.content.KestrosCode;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CodeDataSourceComponent extends BaseDataSourceComponent<KestrosCode> implements
        KestrosCode {
  @Nullable
  @Override
  public String getCode() {
    return getComponentData().getCode();
  }
}