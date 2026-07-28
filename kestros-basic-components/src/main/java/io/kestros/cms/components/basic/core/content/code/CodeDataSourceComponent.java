package io.kestros.cms.components.basic.core.content.code;

import io.kestros.cms.components.basic.api.content.KestrosCode;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosCode} handed to it by an upstream datasource, delegating every value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CodeDataSourceComponent extends BaseDataSourceComponent<KestrosCode> implements
        KestrosCode {
  @Nullable
  @Override
  public String getCode() {
    return getComponentData().getCode();
  }
}