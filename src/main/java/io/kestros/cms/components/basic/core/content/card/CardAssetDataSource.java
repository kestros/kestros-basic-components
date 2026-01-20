package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardAssetDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private Asset asset;
  @OSGiService
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public String getTitle() {
    if (getAsset() != null) {
      return getAsset().getTitle();
    }
    return null;
  }

  @Nullable
  @Override
  public String getDescription() {
    if (getAsset() != null) {
      return getAsset().getDescription();
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    if (getAsset() != null) {
      if (getAsset().getPath() != null) {
        String imagePath = getAsset().getPath();
        String altText = null;
        String caption = null;
        String imageTitle = null;
        String href = null;
        String ariaLabel = null;
        String anchorTitle = null;
        AnchorTarget target = AnchorTarget.SAME_WINDOW;
        String id = null;
        List<ComponentVariation> componentVariations
                = KestrosBasicComponentElement.getAppliedVariations("imageVariations",
                getResource(), KestrosImage.RESOURCE_TYPE,
                getUiFramework(), getComponentVariationRetrievalService(),
                getComponentUiFrameworkViewRetrievalService());
        String layout = KestrosBasicComponentElement.getLayout("imageLayout",
                getResource());
        try {
          return new KestrosImageImpl(imagePath, altText, caption,
                  imageTitle, href, ariaLabel,
                  anchorTitle, target, getResourceResolver(),
                  getUiFramework(), getPath(), componentVariations,
                  layout, id, "imageElement");
        } catch (ComponentConfigurationException e) {
          throw new RuntimeException(e);
        }
      }
      return null;
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    return null;
  }

  @Nullable
  Asset getAsset() {
    if (asset == null) {
      String assetPath = getResource().getValueMap().get("assetPath", String.class);
      if (assetPath != null) {
        try {
          asset = assetRetrievalService.getAsset(assetPath, null, getResourceResolver());
        } catch (AssetRetrievalException e) {
          return null;
        }
      }
    }
    return asset;
  }
}
