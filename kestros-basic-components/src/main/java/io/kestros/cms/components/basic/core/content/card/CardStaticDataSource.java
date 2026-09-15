package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardStaticDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public String getDescription() {
    return getResource().getValueMap().get("description", String.class);
  }

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    Resource titleResource = getResource().getChild("titleElement");
    if (titleResource == null) {
      titleResource = getResource();
    }
    String headingText = titleResource.getValueMap().get("headingText", String.class);
    if (StringUtils.isBlank(headingText)) {
      return null;
    }
    // Heading Level is a select the author never has to touch, so headingType is routinely absent.
    // Reading it through KestrosHeadingImpl(Resource) threw on the missing value and the whole
    // title vanished from the card with no error. A standalone heading survives the same content:
    // heading/common/content.html uses data-sly-element, which falls back to h1. Default it here
    // the way CardAssetDataSource already does.
    String headingType = titleResource.getValueMap().get("headingType", "h1");
    try {
      return new KestrosHeadingImpl(headingText, headingType, this, "title", "titleElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    try {
      Resource imageResource = getResource().getChild("imageElement");
      if (imageResource == null) {
        imageResource = getResource();
      }
      return new KestrosImageImpl(imageResource, this,
              "image",
              "imageElement", assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    Resource buttonGroupResource = getResource().getChild("buttonGroupElement");
    if (buttonGroupResource == null) {
      buttonGroupResource = getResource();
    }
    try {
      return new KestrosButtonGroupImpl(buttonGroupResource,
              this, "buttonGroup", "buttonGroupElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


}