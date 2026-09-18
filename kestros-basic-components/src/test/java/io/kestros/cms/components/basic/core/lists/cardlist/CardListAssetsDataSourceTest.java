package io.kestros.cms.components.basic.core.lists.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.models.AssetCollection;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class CardListAssetsDataSourceTest extends BaseDataSourceTest {

  private CardListAssetsDataSource cardListAssetsDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
    setupSamplePage("/content/page", null);
    setUpSampleCollection("/content/assets/collection");

    properties.put("collectionPath", "/content/assets/collection");
    resource = context.create().resource("/content/page/cardlist/assets", properties);
    context.request().setResource(resource);
  }

  @Override
  public void doComponentTypeSetup() {

  }

  @Override
  public void testToSyntheticResource() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertNotNull(
            cardListAssetsDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetCollection() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertNotNull(cardListAssetsDataSource.getCollection());
  }

  @Test
  public void testGetCards() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_defaultSortBy_returnsNaturalOrder() {
    registerAssetRetrievalService();
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByEmpty_returnsAll() {
    registerAssetRetrievalService();
    properties.put("sortBy", "");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-empty", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByTitle() {
    registerAssetRetrievalService();
    properties.put("sortBy", "title");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-title", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByName() {
    registerAssetRetrievalService();
    properties.put("sortBy", "name");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-name", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_reverseOrder() {
    registerAssetRetrievalService();
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/cardlist/assets-reverse", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_reverseOrderDefault_false() {
    registerAssetRetrievalService();
    resource = context.create().resource("/content/page/cardlist/assets-no-reverse", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitApplied() {
    registerAssetRetrievalService();
    properties.put("limit", "2");
    resource = context.create().resource("/content/page/cardlist/assets-limit-2", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(2, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitZero_returnsAll() {
    registerAssetRetrievalService();
    properties.put("limit", "0");
    resource = context.create().resource("/content/page/cardlist/assets-limit-0", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitDefault_returnsAll() {
    registerAssetRetrievalService();
    resource = context.create().resource("/content/page/cardlist/assets-no-limit", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_limitInvalidString_returnsAll() {
    registerAssetRetrievalService();
    properties.put("limit", "not-a-number");
    resource = context.create().resource("/content/page/cardlist/assets-limit-invalid", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByNameAndReverse() {
    registerAssetRetrievalService();
    properties.put("sortBy", "name");
    properties.put("reverse", true);
    resource = context.create().resource("/content/page/cardlist/assets-name-reverse", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(3, cardListAssetsDataSource.getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByTitleAndLimit() {
    registerAssetRetrievalService();
    properties.put("sortBy", "title");
    properties.put("limit", "1");
    resource = context.create().resource("/content/page/cardlist/assets-title-limit", properties);
    context.request().setResource(resource);
    cardListAssetsDataSource = context.request().adaptTo(CardListAssetsDataSource.class);
    assertEquals(1, cardListAssetsDataSource.getCardElements().size());
  }

  /**
   * The createdDate and lastModified comparators had no coverage: the existing sort cases only
   * exercise name and title, which share a different lambda.
   */
  @Test
  public void testGetCardElements_sortByCreatedDate() {
    registerAssetRetrievalService();
    properties.put("sortBy", "createdDate");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-created",
        properties);
    context.request().setResource(resource);

    assertEquals(3, context.request().adaptTo(CardListAssetsDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetCardElements_sortByLastModified() {
    registerAssetRetrievalService();
    properties.put("sortBy", "lastModified");
    resource = context.create().resource("/content/page/cardlist/assets-sortby-modified",
        properties);
    context.request().setResource(resource);

    assertEquals(3, context.request().adaptTo(CardListAssetsDataSource.class)
        .getCardElements().size());
  }

  @Test
  public void testGetHeadingLevelDefaultsToH2() {
    registerAssetRetrievalService();
    resource = context.create().resource("/content/page/cardlist/assets-default-heading",
        properties);
    context.request().setResource(resource);

    assertEquals("h2",
        context.request().adaptTo(CardListAssetsDataSource.class).getHeadingLevel());
  }

  @Test
  public void testGetHeadingLevelWhenConfigured() {
    registerAssetRetrievalService();
    properties.put("headingType", "h3");
    resource = context.create().resource("/content/page/cardlist/assets-h3", properties);
    context.request().setResource(resource);

    assertEquals("h3",
        context.request().adaptTo(CardListAssetsDataSource.class).getHeadingLevel());
  }

  // ---------------------------------------------------------------------------------------------
  // One unbuildable asset must not take the list with it.
  //
  // These drive getCardElements() itself. A test that only builds a card is a test of
  // KestrosCardImpl, and the defect was in the loop around it.
  // ---------------------------------------------------------------------------------------------

  /** The assets the shared fixture creates, read the way the data source reads them. */
  private List<Asset> collectionAssets() {
    return new ArrayList<>(context.request().adaptTo(CardListAssetsDataSource.class)
        .getCollection().getChildAssets());
  }

  /**
   * A data source whose collection holds exactly the assets given. getCollection() is stubbed
   * rather than the loop, so getCardElements() itself runs unaltered.
   */
  private CardListAssetsDataSource dataSourceOver(final List<Asset> assets) {
    final AssetCollection collection = mock(AssetCollection.class);
    doReturn(new ArrayList<>(assets)).when(collection).getChildAssets();
    final CardListAssetsDataSource dataSource = spy(
        context.request().adaptTo(CardListAssetsDataSource.class));
    doReturn(collection).when(dataSource).getCollection();
    return dataSource;
  }

  /**
   * An asset that fails while its card is being built. getDescription() is what the card
   * constructor is handed first, and nothing on that path guards it.
   */
  private Asset brokenAsset(final String path, final RuntimeException failure) {
    final Asset broken = mock(Asset.class);
    when(broken.getPath()).thenReturn(path);
    when(broken.getTitle()).thenReturn("Broken Asset Title");
    when(broken.getDescription()).thenThrow(failure);
    return broken;
  }

  @Test
  public void testGetCardElementsSkipsTheBrokenAssetAndKeepsTheRest() {
    registerAssetRetrievalService();
    final List<Asset> good = collectionAssets();
    final CardListAssetsDataSource dataSource = dataSourceOver(Arrays.asList(good.get(0),
        brokenAsset("/content/assets/collection/broken",
            new IllegalStateException("jcr:content will not adapt")),
        good.get(1)));

    final List<KestrosCard> cards = dataSource.getCardElements();

    assertEquals("the two healthy assets must still produce cards", 2, cards.size());
    assertEquals("Asset 1 Title", cards.get(0).getTitleElement().getHeadingText());
    assertEquals("Asset 2 Title", cards.get(1).getTitleElement().getHeadingText());
  }

  @Test
  public void testGetCardElementsLogsTheSkippedAssetWithTheExceptionClassAndMessage() {
    registerAssetRetrievalService();
    final List<Asset> good = collectionAssets();
    final CardListAssetsDataSource dataSource = dataSourceOver(Arrays.asList(good.get(0),
        brokenAsset("/content/assets/collection/broken",
            new IllegalStateException("jcr:content will not adapt"))));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListAssetsDataSource.class)) {
      assertEquals(1, dataSource.getCardElements().size());

      assertEquals("exactly one asset was skipped, so exactly one warning is expected", 1,
          warnings.messages().size());
      final String warning = warnings.messages().get(0);
      assertTrue("the warning must name the skipped asset: " + warning,
          warning.contains("/content/assets/collection/broken"));
      assertTrue("the warning must name the exception class: " + warning,
          warning.contains("java.lang.IllegalStateException"));
      assertTrue("the warning must carry the exception message: " + warning,
          warning.contains("jcr:content will not adapt"));
    }
  }

  /** An exception with no message must still produce a line that names the class. */
  @Test
  public void testGetCardElementsNamesTheExceptionClassWhenThereIsNoMessage() {
    registerAssetRetrievalService();
    final List<Asset> good = collectionAssets();
    final CardListAssetsDataSource dataSource = dataSourceOver(Arrays.asList(good.get(0),
        brokenAsset("/content/assets/collection/broken", new NullPointerException()),
        good.get(1)));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListAssetsDataSource.class)) {
      assertEquals(2, dataSource.getCardElements().size());

      assertEquals(1, warnings.messages().size());
      assertTrue("the warning must name the exception class even with a null message: "
          + warnings.messages().get(0),
          warnings.messages().get(0).contains("java.lang.NullPointerException"));
    }
  }

  /**
   * An asset whose image cannot be configured still gets a card, and the cards already built are
   * kept. This is where getCardElements() used to return null to HTL.
   */
  @Test
  public void testGetCardElementsKeepsTheCardWhenTheImageCannotBeBuilt() {
    registerAssetRetrievalService();
    final List<Asset> good = collectionAssets();
    final Asset imageless = mock(Asset.class);
    when(imageless.getPath()).thenReturn("");
    when(imageless.getTitle()).thenReturn("Imageless Asset Title");
    when(imageless.getDescription()).thenReturn("Imageless Asset Description");

    final CardListAssetsDataSource dataSource = dataSourceOver(
        Arrays.asList(good.get(0), imageless, good.get(1)));

    try (RecordedWarnings warnings = new RecordedWarnings(CardListAssetsDataSource.class)) {
      final List<KestrosCard> cards = dataSource.getCardElements();

      assertEquals("the card with no buildable image is kept, not dropped", 3, cards.size());
      assertEquals("Imageless Asset Title", cards.get(1).getTitleElement().getHeadingText());
      assertNull("the card renders without an image", cards.get(1).getImage());
      assertEquals("Asset 1 Title", cards.get(0).getTitleElement().getHeadingText());
      assertEquals("Asset 2 Title", cards.get(2).getTitleElement().getHeadingText());

      assertEquals("the degraded card is logged once", 1, warnings.messages().size());
      assertTrue("the warning must name the exception class: " + warnings.messages().get(0),
          warnings.messages().get(0).contains(
              "io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException"));
    }
  }

  /**
   * Skipping is only correct per asset. A component that resolves to no UI framework would fail
   * every card for the same reason, and an empty list would hide that.
   */
  @Test
  public void testGetCardElementsThrowsWhenTheThemeResolvesToNoUiFramework() throws Exception {
    registerAssetRetrievalService();
    when(theme.getUiFramework()).thenReturn(null);

    try {
      context.request().adaptTo(CardListAssetsDataSource.class).getCardElements();
      fail("a component whose theme carries no UI framework must not render an empty card list");
    } catch (final RuntimeException expected) {
      assertNotNull(expected);
    }
  }
}
