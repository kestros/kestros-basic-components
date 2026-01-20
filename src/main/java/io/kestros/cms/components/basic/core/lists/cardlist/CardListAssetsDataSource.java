package io.kestros.cms.components.basic.core.lists.cardlist;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.models.AssetCollection;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.card.KestrosCardImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardListAssetsDataSource extends BaseContainerSlingModelDataSource implements
        KestrosCardList {
  @OSGiService
  private AssetRetrievalService assetRetrievalService;
  private AssetCollection collection;

  AssetCollection getCollection() {
    if (collection == null) {
      String collectionPath = getResource().getValueMap().get("collectionPath", String.class);
      try {
        collection = assetRetrievalService.getCollection(collectionPath, getResourceResolver());
      } catch (AssetCollectionRetrievalException e) {
        return null;
      }
    }
    return collection;
  }

  @Nonnull
  @Override
  public List<KestrosCard> getCards() {
    List<KestrosCard> cards = new ArrayList<>();
    String parentPath = getPath();
    for (Asset asset : getCollection().getChildAssets()) {
      String imagePath = asset.getPath();
      String altText = null;
      String caption = null;
      String imageTitle = null;
      String href = null;
      String ariaLabel = null;
      String anchorTitle = null;
      AnchorTarget target = null;

      List<ComponentVariation> imageVariations = KestrosBasicComponentElement.getAppliedVariations(
              "imageVariations",
              getResource(),
              KestrosImage.RESOURCE_TYPE,
              getUiFramework(),
              getComponentVariationRetrievalService(),
              getComponentUiFrameworkViewRetrievalService());
      String imageLayout = KestrosBasicComponentElement.getLayout("imageLayout", getResource());
      String imageId = null;
      KestrosImage image = null;
      try {
        image = new KestrosImageImpl(imagePath, altText, caption, imageTitle,
                href, ariaLabel, anchorTitle, target,
                getResourceResolver(), getUiFramework(), parentPath, imageVariations, imageLayout,
                imageId, "imageElement");
      } catch (ComponentConfigurationException e) {
        return null;
      }
      try {
        cards.add(
                new KestrosCardImpl(asset.getTitle(), asset.getDescription(), image,
                        null,
                        getResourceResolver(), getUiFramework(), parentPath,
                        KestrosBasicComponentElement.getAppliedVariations("cardVariations",
                                getResource(),
                                "/libs/kestros/commons/components/content/card",
                                getUiFramework(),
                                getComponentVariationRetrievalService(),
                                getComponentUiFrameworkViewRetrievalService()),
                        KestrosBasicComponentElement.getLayout("cardLayout", getResource()),
                        null, null));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ArrayList<>(cards);
  }

}
