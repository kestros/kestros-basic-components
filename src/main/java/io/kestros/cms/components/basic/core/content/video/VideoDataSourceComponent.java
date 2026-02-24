package io.kestros.cms.components.basic.core.content.video;

import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoDataSourceComponent extends BaseDataSourceComponent<KestrosVideo>
    implements KestrosVideo {

  @Nullable
  @Override
  public String getVideoSource() {
    return getComponentData().getVideoSource();
  }

  @Nonnull
  @Override
  public String getFallbackText() {
    return getComponentData().getFallbackText();
  }
}
