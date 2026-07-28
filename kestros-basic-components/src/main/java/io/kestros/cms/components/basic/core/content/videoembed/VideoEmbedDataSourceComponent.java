package io.kestros.cms.components.basic.core.content.videoembed;

import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Renders a {@link KestrosVideoEmbed} handed to it by an upstream datasource, delegating every
 * value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoEmbedDataSourceComponent extends BaseDataSourceComponent<KestrosVideoEmbed>
    implements KestrosVideoEmbed {

  @Nullable
  @Override
  public String getVideoEmbedCode() {
    String embedCode = getComponentData().getVideoEmbedCode();
    if (embedCode == null) {
      return null;
    }
    // Defense-in-depth: sanitize embed code before returning to HTL.
    // Even though datasources should return safe HTML, this guard protects
    // against future datasource variants that may not validate as rigorously.
    return EmbedSanitizer.sanitize(embedCode);
  }
}
