package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardListChildPagesDataSourceTest extends BaseDataSourceTest {
  private CardListChildPagesDataSource cardListChildPagesDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setUpSampleCollection("/content/collection");
    setupSamplePage("/content/page", "/content/collection/asset-1");

    properties.put("pagesPath", "/content/page");
    resource = context.create().resource("/content/page/jcr:content/component", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
  }

  @Override
  public void doComponentTypeSetup() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("jcr:primaryType", "kes:ComponentType");
    context.create().resource(KestrosCardList.RESOURCE_TYPE, properties);
    context.create().resource(KestrosCard.RESOURCE_TYPE, properties);
    context.create().resource(KestrosImage.RESOURCE_TYPE, properties);
    context.create().resource(KestrosButtonGroup.RESOURCE_TYPE, properties);
    context.create().resource(KestrosButton.RESOURCE_TYPE, properties);
  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(
            cardListChildPagesDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetRootPage() {
    assertNotNull(cardListChildPagesDataSource.getRootPage());
  }

  @Test
  public void testGetCards() {
    properties.put("cardVariations", new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    properties.put("imageVariations", new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    properties.put("buttonGroupVariations", new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    resource = context.create().resource("/content/page/jcr:content/component2", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCards().size());
    assertEquals(3, cardListChildPagesDataSource.getCards().get(0).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCards().get(0).getImageElement().getVariations()
            .size());

    assertEquals(3,
            cardListChildPagesDataSource.getCards().get(0).getButtonGroupElement().getVariations()
                    .size());
    assertEquals(3, cardListChildPagesDataSource.getCards().get(1).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCards().get(1).getImageElement().getVariations()
            .size());
    assertEquals(3,
            cardListChildPagesDataSource.getCards().get(1).getButtonGroupElement().getVariations()
                    .size());
    assertEquals(3, cardListChildPagesDataSource.getCards().get(2).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCards().get(2).getImageElement().getVariations()
            .size());
    assertEquals(3,
            cardListChildPagesDataSource.getCards().get(2).getButtonGroupElement().getVariations()
                    .size());
    context.request().setResource(
            cardListChildPagesDataSource.getCards().get(0).getImageElement().getResource());
    assertEquals(3, context.request().adaptTo(ImageStaticDataSource.class).getVariations().size());
  }

  @Test
  public void testGetChildren() {
    assertEquals(3, cardListChildPagesDataSource.getChildren().size());
  }
}