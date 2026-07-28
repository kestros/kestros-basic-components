package io.kestros.cms.components.basic.core.content.text;

import io.kestros.cms.components.basic.api.content.KestrosText;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;


/**
 * Renders a {@link KestrosText} handed to it by an upstream datasource, delegating every value to
 * that element rather than reading the resource itself.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class TextDataSourceComponent extends BaseDataSourceComponent<KestrosText>
    implements KestrosText {

  @Nullable
  @Override
  public String getText() {
    return getComponentData().getText();
  }
}
