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

@SuppressFBWarnings(value = "IMC_IMMATURE_CLASS_NO_TOSTRING",
    justification = "A style rule rather than a defect: the detector fires on every class that"
        + " does not declare toString. The fields here are author content read from the"
        + " repository and are reached through the getters HTL calls, so a default rendering of"
        + " them is not something to add.")
public class KestrosContainerImpl extends BaseContainerSyntheticResource
    implements KestrosContainer {

  private final List<KestrosBasicComponentElement> childElements;

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
