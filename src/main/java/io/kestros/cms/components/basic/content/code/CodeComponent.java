package io.kestros.cms.components.basic.content.code;

import io.kestros.cms.filetypes.HtmlFile;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class,
    resourceType = "kestros/commons/components/content/code")
public class CodeComponent extends BaseComponent {

  private static final Logger LOG = LoggerFactory.getLogger(CodeComponent.class);

  @Nullable
  public String getCode() {
    if (StringUtils.isNotEmpty(getCodePropertyValue())) {
      return getCodePropertyValue();
    } else if (StringUtils.isNotEmpty(getCodeResourcePropertyValue())) {
      LOG.info("Code resource property value: {}", getCodeResourcePropertyValue());
      if (getCodeResource() != null && getCodeResource().getResourceType().equals("nt:file")) {
        HtmlFile file = getCodeResource().adaptTo(HtmlFile.class);
        LOG.info("Found html file: {}", file);
        try {
          // escape
          return file.getFileContent();
        } catch (Exception e) {
          LOG.error("Error reading file content", e);
        }
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
