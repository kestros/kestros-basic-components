package io.kestros.cms.components.basic.core.structure.accordion;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosAccordionPanel;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic {@link KestrosAccordionPanel}, built in code by a datasource rather than adapted
 * from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosAccordionPanelImpl extends BaseSyntheticResource
    implements KestrosAccordionPanel {

  private final String title;
  private final Boolean expanded;
  private final Boolean disabled;
  private final String ariaLabel;

  /**
   * Constructs an accordion panel impl.
   *
   * @param title Title.
   * @param expanded Expanded.
   * @param disabled Disabled.
   * @param ariaLabel Aria label.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosAccordionPanelImpl(
      @Nonnull String title,
      @Nonnull Boolean expanded,
      @Nonnull Boolean disabled,
      @Nonnull String ariaLabel,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.title = title;
    this.expanded = expanded;
    this.disabled = disabled;
    this.ariaLabel = ariaLabel;
  }

  @Nullable
  @Override
  public String getTitle() {
    return title;
  }

  @Nonnull
  @Override
  public Boolean isExpanded() {
    return expanded;
  }

  @Nonnull
  @Override
  public Boolean isDisabled() {
    return disabled;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return ariaLabel;
  }
}