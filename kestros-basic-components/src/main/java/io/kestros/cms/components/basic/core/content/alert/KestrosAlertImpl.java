package io.kestros.cms.components.basic.core.content.alert;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic {@link KestrosAlert}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosAlertImpl extends BaseSyntheticResource implements KestrosAlert {

  private String heading;
  private String text;

  /**
   * Constructs an alert impl.
   *
   * @param heading Heading.
   * @param text Text.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosAlertImpl(String heading, String text,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.heading = heading;
    this.text = text;
  }

  @Nullable
  @Override
  public String getHeading() {
    return heading;
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }
}
