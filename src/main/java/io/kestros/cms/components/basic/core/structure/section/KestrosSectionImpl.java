package io.kestros.cms.components.basic.core.structure.section;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.structure.KestrosSection;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.structure.container.KestrosContainerImpl;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KestrosSectionImpl extends KestrosContainerImpl implements KestrosSection {

  private String backgroundImage;

  public KestrosSectionImpl(
      @Nullable String backgroundImage,
      @Nonnull List<KestrosBasicComponentElement> childElements,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(childElements, dataSource, resourcePrefix, forcedResourceName);
  }

  @Nullable
  @Override
  public String getBackgroundImage() {
    return backgroundImage;
  }
}
