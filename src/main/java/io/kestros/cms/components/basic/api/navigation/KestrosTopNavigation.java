package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import javax.annotation.Nullable;

public interface KestrosTopNavigation extends KestrosContainerElement {
  @Nullable
  KestrosImage getLogo();

}
