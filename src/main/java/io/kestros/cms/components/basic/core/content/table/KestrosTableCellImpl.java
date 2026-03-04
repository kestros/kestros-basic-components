package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTableCell;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Synthetic table cell implementation for programmatic table construction.
 * Holds a text value as its content.
 */
public class KestrosTableCellImpl extends BaseContainerSyntheticResource implements
    KestrosTableCell {

  private final String textValue;

  public KestrosTableCellImpl(@Nonnull String textValue,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix,
      @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.textValue = textValue;
  }

  @Nonnull
  public String getTextValue() {
    return textValue;
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getCellContentElements() {
    return new ArrayList<>();
  }
}
