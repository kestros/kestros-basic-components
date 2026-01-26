package io.kestros.cms.components.basic.core.content.text;

import io.kestros.cms.components.basic.api.content.TextElementType;
import io.kestros.cms.components.basic.api.content.KestrosText;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class)
public class TextStaticDataSource extends BaseSlingModelDataSource implements KestrosText {

  @Nonnull
  @Override
  public TextElementType getType() {
    return null;
  }

  @Nullable
  @Override
  public String getText() {
    return getResource().getValueMap().get("text", String.class);
  }
}
