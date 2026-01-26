package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardStaticDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  @Nullable
  @Override
  public String getTitle() {
    return getResource().getValueMap().get("title", String.class);
  }

  @Nullable
  @Override
  public String getDescription() {
    return getResource().getValueMap().get("description", String.class);
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    try {
      Resource imageResource = getResource().getChild("imageElement");
      if (imageResource == null) {
        imageResource = getResource();
      }
      return new KestrosImageImpl(imageResource, getResourceResolver(), getUiFramework(), getPath(),
              KestrosBasicComponentElement.getAppliedVariations("imageVariations",
                      getResource(),
                      KestrosImage.RESOURCE_TYPE,
                      getUiFramework(),
                      getComponentVariationRetrievalService(),
                      getComponentUiFrameworkViewRetrievalService()),
              KestrosBasicComponentElement.getLayout("imageLayout",
                      imageResource), "imageElement");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    try {
      return new KestrosButtonGroupImpl(getResource(),
              KestrosBasicComponentElement.getAppliedVariations("buttonVariations",
                      getResource(), KestrosButton.RESOURCE_TYPE, getUiFramework(),
                      getComponentVariationRetrievalService(),
                      getComponentUiFrameworkViewRetrievalService()),
              getResourceResolver(), getUiFramework(),
              getPath(),
              KestrosBasicComponentElement.getAppliedVariations("buttonGroupVariations",
                      getResource(),
                      KestrosButtonGroup.RESOURCE_TYPE,
                      getUiFramework(),
                      getComponentVariationRetrievalService(),
                      getComponentUiFrameworkViewRetrievalService()),
              KestrosBasicComponentElement.getLayout("buttonGroupLayout",
                      getResource()), null, "buttonGroup");
    } catch (ComponentConfigurationException e) {
      return null;
    }
  }


}