package io.kestros.cms.components.basic.core.content.alert;

import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
