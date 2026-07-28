package io.kestros.cms.components.basic.core;

import javax.annotation.Nonnull;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;

public abstract class BaseComponentSlingModel extends BaseComponent implements
        KestrosBasicComponentElement {
  @Override
  @Nonnull
  public Boolean isSynthetic() {
    return false;
  }
}
