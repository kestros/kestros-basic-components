package io.kestros.cms.components.basic.core.content.video;

import io.kestros.cms.components.basic.api.content.KestrosVideo;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosVideo} handed to it by an upstream datasource, delegating every value to
 * that element rather than reading the resource itself.
 */
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
