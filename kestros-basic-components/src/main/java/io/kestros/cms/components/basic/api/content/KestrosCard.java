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

  @JsonIgnore
  @Nullable
  default Resource getTitle() {
    if (getResource().getChild("titleElement") != null) {
      return getResource().getChild("titleElement");
    }
    KestrosHeading titleElement = getTitleElement();
    if (titleElement == null) {
      return null;
    }
    return titleElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  /**
   * Card title.
   *
   * @return Title or null.
   */
  @Nullable
  KestrosHeading getTitleElement();

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
    if (getResource().getChild("imageElement") != null) {
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
    if (getResource().getChild("buttonGroupElement") != null) {
      return getResource().getChild("buttonGroupElement");
    }
    KestrosButtonGroup buttonGroupElement = getButtonGroupElement();
    if (buttonGroupElement == null) {
      return null;
    }
    return buttonGroupElement.toSyntheticResource(getResourceResolver(), getPath());
  }

  @Nullable
  KestrosButtonGroup getButtonGroupElement();

  /**
   * Returns the first button in the button group, used for clickable card layouts.
   *
   * @return Primary button or null.
   */
  @Nullable
  default KestrosButton getPrimaryButton() {
    KestrosButtonGroup group = getButtonGroupElement();
    if (group != null && !group.getButtonsElements().isEmpty()) {
      return group.getButtonsElements().get(0);
    }
    return null;
  }

  @Nonnull
  @Override
  default List<KestrosBasicComponentElement> getChildElements() {
    List<KestrosBasicComponentElement> childElements = new ArrayList<>();
    KestrosHeading headingElement = getTitleElement();
    if (headingElement != null) {
      childElements.add(headingElement);
    }
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