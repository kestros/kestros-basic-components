package io.kestros.cms.components.basic.core.content.richtext;

import io.kestros.cms.components.basic.api.content.KestrosRichText;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class KestrosRichTextStaticDataSource extends BaseSlingModelDataSource
    implements KestrosRichText {

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
