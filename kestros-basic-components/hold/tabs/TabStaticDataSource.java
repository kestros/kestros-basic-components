package io.kestros.cms.components.basic.core.structure.tabs;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosTab;
import io.kestros.cms.components.basic.api.structure.KestrosTabContent;
import io.kestros.cms.components.basic.api.structure.KestrosTabHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TabStaticDataSource extends BaseSlingModelDataSource implements KestrosTab {

  @Override
  public KestrosTabHeader getTabHeaderElement() {
    try {
      return new KestrosTabHeaderImpl(getTitle(), getContentId(), isActive(), this, "tabHeader",
              "tabHeaderElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


  @Override
  public KestrosTabContent getTabContentElement() {
    try {
      return new KestrosTabContentImpl(this, "tabContent", "tabContentElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


  String getTitle() {
    return getResource().getValueMap().get("tabTitle", String.class);
  }

  String getContentId() {
    return "TODO";
  }

  Boolean isActive() {
    return getResource().getValueMap().get("active", Boolean.class);
  }
}
