package io.kestros.cms.components.basic.core.content.videoembed;

import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoEmbedDataSourceComponent extends BaseDataSourceComponent<KestrosVideoEmbed>
    implements KestrosVideoEmbed {

  @Nullable
  @Override
  public String getVideoEmbedCode() {
    return getComponentData().getVideoEmbedCode();
  }
}
