package io.kestros.cms.components.basic.api.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.KestrosContainerElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.Resource;

/**
 * Kestros Card component API.
 */
public interface KestrosCard extends KestrosContainerElement {
  String RESOURCE_TYPE = "/libs/kestros/commons/components/content/card";

  @Override
  default String getComponentResourceType() {
    return RESOURCE_TYPE;
  }

  /**
   * Card title.
   *
   * @return Title or null.
   */
  @Nullable
  String getTitle();

  /**
   * Card description.
   *
   * @return Description or null.
   */
  @Nullable
  String getDescription();

  /**
   * Provides a synthetic resource for the card image.
   *
   * @return Image resource or null.
   */
  @JsonIgnore
  @Nullable
  default Resource getImage() {
    if(getResource().getChild("imageElement") != null) {
      return getResource().getChild("imageElement");
    }
    KestrosImage imageElement = getImageElement();
    if (imageElement == null) {
      return null;
    }
    return imageElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  @Nullable
  KestrosImage getImageElement();

  /**
   * Provides a synthetic resource for the button card group.
   *
   * @return Button group resource or null.
   */
  @JsonIgnore
  @Nullable
  default Resource getButtonGroup() {
    if(getResource().getChild("buttonGroup") != null) {
      return getResource().getChild("buttonGroup");
    }
    KestrosButtonGroup buttonGroupElement = getButtonGroupElement();
    if (buttonGroupElement == null) {
      return null;
    }
    return buttonGroupElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  @Nullable
  KestrosButtonGroup getButtonGroupElement();

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    List<KestrosBasicComponentElement> childElements = new ArrayList<>();
    KestrosImage imageElement = getImageElement();
    if (imageElement != null) {
      childElements.add(imageElement);
    }
    KestrosButtonGroup buttonGroupElement = getButtonGroupElement();
    if (buttonGroupElement != null) {
      childElements.add(buttonGroupElement);
    }
    return childElements;
  }
}