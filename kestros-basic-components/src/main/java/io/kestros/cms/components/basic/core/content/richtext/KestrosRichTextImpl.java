package io.kestros.cms.components.basic.core.content.richtext;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosRichText;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import io.kestros.cms.components.basic.core.TextSanitizer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosRichTextImpl extends BaseSyntheticResource implements KestrosRichText {

  private String text;

  public KestrosRichTextImpl(
      @Nonnull String text,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = TextSanitizer.escapeMultiByteToEntities(text);
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }
}
