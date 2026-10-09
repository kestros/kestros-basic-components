package io.kestros.cms.components.basic.core.content.table;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.table.KestrosTableHeader;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic (synthetic) {@link KestrosTableHeader}, letting a datasource build table headers in
 * code rather than from authored resources. Mirrors
 * {@link io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl} (a text-only leaf).
 */
public class KestrosTableHeaderImpl extends BaseSyntheticResource implements KestrosTableHeader {

  private final String text;

  /**
   * Constructs a synthetic table header.
   *
   * @param text                header text.
   * @param dataSource          owning datasource.
   * @param resourcePrefix      resource prefix (for variation/layout resolution).
   * @param forcedResourceName  synthetic resource name (unique within the table).
   * @throws ComponentConfigurationException if the synthetic resource cannot be configured.
   */
  public KestrosTableHeaderImpl(@Nullable final String text,
      @Nonnull final BaseSlingModelDataSource dataSource, @Nonnull final String resourcePrefix,
      @Nullable final String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = text;
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }
}
