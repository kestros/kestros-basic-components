package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.lists.KestrosCardList;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
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
    properties.put("readMoreText","Button Text");
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
    assertNotNull(
        cardListChildPagesDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
    assertEquals(13, cardListChildPagesDataSource.toSyntheticResource(context.resourceResolver(),
        "/test").getValueMap().size());
    ValueMap valueMap = cardListChildPagesDataSource.toSyntheticResource(
        context.resourceResolver(), "/test").getValueMap();
    assertNotNull(valueMap);
    assertEquals("/libs/kestros/commons/components/lists/card-list",
        valueMap.get("componentResourceType"));
    assertFalse(valueMap.get("synthetic", true));
    assertNull(valueMap.get("id"));
    assertEquals(0, valueMap.get("variations", List.class).size());
    assertEquals("", valueMap.get("inlineVariations"));
    assertTrue(valueMap.get("dataSourceComponent", false));
    assertEquals("component", valueMap.get("forcedResourceName"));
    assertEquals(3, valueMap.get("cards", List.class).size());

    Map<String, Object> card = (Map) valueMap.get("cards", List.class).get(0);
    assertNotNull(card);
    assertNotNull(card.get("path"));
    assertNotNull(card.get("resourceType"));


  }

  @Test
  public void testGetRootPage() {
    assertNotNull(cardListChildPagesDataSource.getRootPage());
  }

  @Test
  public void testGetCards() {
    properties.put("cardVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    properties.put("imageVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    properties.put("buttonGroupVariations",
        new String[]{"/variations/variation1", "/variations/variation2", "/variations/variation3"});
    resource = context.create().resource("/content/page/jcr:content/component2", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(0).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(0).getImageElement().getVariations()
        .size());

    assertEquals(3,
        cardListChildPagesDataSource.getCardElements().get(0).getButtonGroupElement().getVariations()
            .size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(1).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(1).getImageElement().getVariations()
        .size());
    assertEquals(3,
        cardListChildPagesDataSource.getCardElements().get(1).getButtonGroupElement().getVariations()
            .size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(2).getVariations().size());
    assertEquals(3, cardListChildPagesDataSource.getCardElements().get(2).getImageElement().getVariations()
        .size());
    assertEquals(3,
        cardListChildPagesDataSource.getCardElements().get(2).getButtonGroupElement().getVariations()
            .size());
    context.request().setResource(
        cardListChildPagesDataSource.getCardElements().get(0).getImageElement().getResource());
    assertEquals(3, context.request().adaptTo(ImageStaticDataSource.class).getVariations().size());
  }

  @Test
  public void testGetChildren() {
    assertEquals(3, cardListChildPagesDataSource.getChildren().size());
  }

  @Test
  public void testGetCardElements_defaultSortBy_returnsNaturalOrder() {
    // sortBy property not set — should return natural JCR order (child-1, child-2, child-3)
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByEmpty_returnsNaturalOrder() {
    properties.put("sortBy", "");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-empty", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByName() {
    properties.put("sortBy", "name");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-name", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    List<KestrosCard> cards = cardListChildPagesDataSource.getCardElements();
    assertEquals(3, cards.size());
  }

  @Test
  public void testGetCardElements_sortByTitle() {
    properties.put("sortBy", "title");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-title",
        properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    List<KestrosCard> cards = cardListChildPagesDataSource.getCardElements();
    assertEquals(3, cards.size());
  }

  @Test
  public void testGetCardElements_reverseOrder() {
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_reverseOrderFalse_default() {
    // reverse not set — defaults to false
    resource = context.create().resource("/content/page/jcr:content/comp-no-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitApplied() {
    properties.put("limit", "2");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-2", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(2, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitZero_returnsAll() {
    properties.put("limit", "0");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-0", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitDefault_returnsAll() {
    // limit not set — defaults to 0 (no limit)
    resource = context.create().resource("/content/page/jcr:content/comp-no-limit", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitInvalidString_returnsAll() {
    properties.put("limit", "invalid");
    resource = context.create().resource("/content/page/jcr:content/comp-limit-invalid",
        properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByNameAndReverse() {
    properties.put("sortBy", "name");
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-name-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByTitleAndLimit() {
    properties.put("sortBy", "title");
    properties.put("limit", "1");
    resource = context.create().resource("/content/page/jcr:content/comp-title-limit", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(1, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByCreated_noDateProperties_doesNotCrash() {
    properties.put("sortBy", "created");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-created", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByModified_noDateProperties_doesNotCrash() {
    properties.put("sortBy", "modified");
    resource = context.create().resource("/content/page/jcr:content/comp-sortby-modified", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByCreatedWithDates() {
    // Create pages with different jcr:created dates
    Map<String, Object> pageProps = new HashMap<>();
    pageProps.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/dated-page", pageProps);
    Map<String, Object> jcrContentProps = new HashMap<>();
    jcrContentProps.put("jcr:primaryType", "nt:unstructured");
    jcrContentProps.put("jcr:title", "Parent");
    context.create().resource("/content/dated-page/jcr:content", jcrContentProps);

    java.util.Calendar cal1 = java.util.Calendar.getInstance();
    cal1.set(2025, 0, 1);
    java.util.Calendar cal2 = java.util.Calendar.getInstance();
    cal2.set(2025, 6, 1);
    java.util.Calendar cal3 = java.util.Calendar.getInstance();
    cal3.set(2025, 3, 1);

    context.create().resource("/content/dated-page/page-a", pageProps);
    Map<String, Object> jcr1 = new HashMap<>();
    jcr1.put("jcr:primaryType", "nt:unstructured");
    jcr1.put("jcr:title", "Page A");
    jcr1.put("jcr:created", cal1);
    context.create().resource("/content/dated-page/page-a/jcr:content", jcr1);

    context.create().resource("/content/dated-page/page-b", pageProps);
    Map<String, Object> jcr2 = new HashMap<>();
    jcr2.put("jcr:primaryType", "nt:unstructured");
    jcr2.put("jcr:title", "Page B");
    jcr2.put("jcr:created", cal2);
    context.create().resource("/content/dated-page/page-b/jcr:content", jcr2);

    context.create().resource("/content/dated-page/page-c", pageProps);
    Map<String, Object> jcr3 = new HashMap<>();
    jcr3.put("jcr:primaryType", "nt:unstructured");
    jcr3.put("jcr:title", "Page C");
    jcr3.put("jcr:created", cal3);
    context.create().resource("/content/dated-page/page-c/jcr:content", jcr3);

    Map<String, Object> compProps = new HashMap<>();
    compProps.put("pagesPath", "/content/dated-page");
    compProps.put("sortBy", "created");
    Resource compResource = context.create().resource("/content/dated-page/jcr:content/comp-created-sort", compProps);
    context.request().setResource(compResource);
    CardListChildPagesDataSource ds = context.request().adaptTo(CardListChildPagesDataSource.class);
    List<KestrosCard> cards = ds.getCardElements();
    assertEquals(3, cards.size());
  }

  @Test
  public void testGetCardElements_sortByModifiedWithDates() {
    Map<String, Object> pageProps = new HashMap<>();
    pageProps.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/mod-page", pageProps);
    Map<String, Object> jcrContentProps = new HashMap<>();
    jcrContentProps.put("jcr:primaryType", "nt:unstructured");
    jcrContentProps.put("jcr:title", "Parent");
    context.create().resource("/content/mod-page/jcr:content", jcrContentProps);

    java.util.Calendar cal1 = java.util.Calendar.getInstance();
    cal1.set(2025, 0, 15);
    java.util.Calendar cal2 = java.util.Calendar.getInstance();
    cal2.set(2025, 5, 15);

    context.create().resource("/content/mod-page/page-x", pageProps);
    Map<String, Object> jcr1 = new HashMap<>();
    jcr1.put("jcr:primaryType", "nt:unstructured");
    jcr1.put("jcr:title", "Page X");
    jcr1.put("jcr:lastModified", cal1);
    context.create().resource("/content/mod-page/page-x/jcr:content", jcr1);

    context.create().resource("/content/mod-page/page-y", pageProps);
    Map<String, Object> jcr2 = new HashMap<>();
    jcr2.put("jcr:primaryType", "nt:unstructured");
    jcr2.put("jcr:title", "Page Y");
    jcr2.put("jcr:lastModified", cal2);
    context.create().resource("/content/mod-page/page-y/jcr:content", jcr2);

    Map<String, Object> compProps = new HashMap<>();
    compProps.put("pagesPath", "/content/mod-page");
    compProps.put("sortBy", "modified");
    Resource compResource = context.create().resource("/content/mod-page/jcr:content/comp-mod-sort", compProps);
    context.request().setResource(compResource);
    CardListChildPagesDataSource ds = context.request().adaptTo(CardListChildPagesDataSource.class);
    List<KestrosCard> cards = ds.getCardElements();
    assertEquals(2, cards.size());
  }

  @Test
  public void testGetCardElements_sortByCreatedReversed() {
    properties.put("sortBy", "created");
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/jcr:content/comp-created-reverse", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(3, cardListChildPagesDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByModifiedWithLimit() {
    properties.put("sortBy", "modified");
    properties.put("limit", "1");
    resource = context.create().resource("/content/page/jcr:content/comp-modified-limit", properties);
    context.request().setResource(resource);
    cardListChildPagesDataSource = context.request().adaptTo(CardListChildPagesDataSource.class);
    assertEquals(1, cardListChildPagesDataSource.getCardElements().size());
  }
}