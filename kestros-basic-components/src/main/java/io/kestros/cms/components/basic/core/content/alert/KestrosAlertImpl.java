package io.kestros.cms.components.basic.core.content.alert;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A style rule rather than a defect: the detector fires on every class that"
        + " does not declare toString. The fields here are author content read from the"
        + " repository and are reached through the getters HTL calls, so a default rendering of"
        + " them is not something to add.")
public class KestrosAlertImpl extends BaseSyntheticResource implements KestrosAlert {

  private String heading;
  private String text;

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
