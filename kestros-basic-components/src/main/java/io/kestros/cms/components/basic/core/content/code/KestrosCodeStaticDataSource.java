package io.kestros.cms.components.basic.core.content.code;

import io.kestros.cms.components.basic.api.content.KestrosCode;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class KestrosCodeStaticDataSource extends BaseSlingModelDataSource implements KestrosCode {
  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  @Override
  @Nonnull
  public String getComponentResourceType() {
    return "/libs/kestros/commons/components/content/code";
  }

  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }

  @Nullable
  @Override
  public String getCode() {
    return getResource().getValueMap().get("code", String.class);
  }
}
