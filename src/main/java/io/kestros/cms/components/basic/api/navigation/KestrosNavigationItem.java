package io.kestros.cms.components.basic.api.navigation;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public interface KestrosNavigationItem extends KestrosLink, KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/navigation/navigation-item";

  @Nonnull
  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  Boolean isActive();

  @Nonnull
  List<KestrosNavigationItem> getNavigationItems();

  @Nonnull
  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getNavigationItems());
  }
}
