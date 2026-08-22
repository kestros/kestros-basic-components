package io.kestros.cms.components.basic.core.content.code;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosCode;
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
public class KestrosCodeImpl extends BaseSyntheticResource implements KestrosCode {

  private final String code;

  public KestrosCodeImpl(@Nonnull String code,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, @Nullable String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.code = code;
  }


  @Nullable
  @Override
  public String getCode() {
    return code;
  }
}
