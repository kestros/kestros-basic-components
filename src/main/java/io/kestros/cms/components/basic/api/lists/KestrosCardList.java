package io.kestros.cms.components.basic.api.lists;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public interface KestrosCardList extends KestrosContainerElement {

  String RESOURCE_TYPE = "/libs/kestros/commons/components/lists/card-list";

  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  @Nonnull
  List<KestrosCard> getCards();

  default List<KestrosBasicComponentElement> getChildElements() {
    return new ArrayList<>(getCards());
  }
}