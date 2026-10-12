package io.kestros.cms.components.basic.core;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import javax.annotation.Nonnull;

public abstract class BaseComponentSlingModel extends BaseComponent implements
        KestrosBasicComponentElement {
  @Nonnull
  @Override
  public Boolean isSynthetic() {
    return Boolean.FALSE;
  }
}
