package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.List;
import javax.annotation.Nonnull;

public interface KestrosNavigation extends KestrosBasicComponentElement {

  @Nonnull
  List<KestrosLink> getNavigationLinks();
}
