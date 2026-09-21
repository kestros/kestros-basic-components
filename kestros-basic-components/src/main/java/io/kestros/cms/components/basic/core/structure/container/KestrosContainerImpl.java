package io.kestros.cms.components.basic.core.structure.container;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosContainer;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class KestrosContainerImpl extends BaseContainerSyntheticResource
    implements KestrosContainer {

  private final List<KestrosBasicComponentElement> childElements;

  @SuppressFBWarnings(value = "OPM_OVERLY_PERMISSIVE_METHOD",
      justification = "This package is exported by the bundle at 0.3.2, so the constructor is "
          + "published API. Narrowing it to protected breaks that API for consumers the detector "
          + "cannot see, since fb-contrib counts callers inside this module only. Ruled by Danny, "
          + "2026-09-13.")
  public KestrosContainerImpl(
      @Nonnull List<KestrosBasicComponentElement> childElements,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.childElements = new ArrayList<>(childElements);
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(childElements);
  }
}
