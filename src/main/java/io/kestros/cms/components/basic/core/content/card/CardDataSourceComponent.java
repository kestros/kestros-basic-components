package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardDataSourceComponent extends
        BaseContainerDataSourceComponent<KestrosCard> implements KestrosCard {

  private String title;
  private String description;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;

  @Nullable
  @Override
  public String getTitle() {
    if (title == null) {
      title = getComponentData().getTitle();
    }
    return title;
  }

  @Nullable
  @Override
  public String getDescription() {
    if (description == null) {
      description = getComponentData().getDescription();
    }
    return description;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    if (image == null) {
      image = getComponentData().getImageElement();
    }
    return image;
  }


  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    if (buttonGroup == null) {
      buttonGroup = getComponentData().getButtonGroupElement();
    }
    return buttonGroup;
  }


}