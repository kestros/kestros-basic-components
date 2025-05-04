package io.kestros.cms.components.basic.content.code;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/code")
public class CodeComponent extends BaseComponent {

  @Nullable
  public String getCode() {
    if (StringUtils.isNotEmpty(getCodePropertyValue())) {
      return getCodePropertyValue();
    } else if (StringUtils.isNotEmpty(getCodeResourcePropertyValue())) {
      if (getCodeResource() != null && getCodeResource().getResourceType().equals("nt:File")) {

      }
    }
    return null;
  }

  String getCodePropertyValue() {
    return getProperty("code", "");
  }

  String getCodeResourcePropertyValue() {
    return getResource().getValueMap().get("codeResource", "");
  }

  Resource getCodeResource() {
    return getResource().getResourceResolver().getResource(getCodeResourcePropertyValue());
  }
}
