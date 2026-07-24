package io.kestros.cms.components.basic.core.content.richtext;

import io.kestros.cms.components.basic.api.content.KestrosRichText;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import io.kestros.cms.components.basic.core.TextSanitizer;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;


@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class RichTextDataSourceComponent extends BaseDataSourceComponent<KestrosRichText>
    implements KestrosRichText {

  @Nullable
  @Override
  public String getText() {
    return TextSanitizer.escapeMultiByteToEntities(getComponentData().getText());
  }
}
