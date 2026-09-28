package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class CardPageDataSourceTest extends BaseDataSourceTest {

  private CardPageDataSource cardPageDataSource;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() {
    registerAssetRetrievalService();
    properties.put("pagePath", "/content/page");
    resource = context.create().resource("/content/page-card", properties);
    context.request().setResource(resource);

    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);
    setupSamplePage("/content/page", null);
  }

  @Override
  public void doComponentTypeSetup() {

  }

  @Override
  public void testToSyntheticResource() {
    assertNotNull(cardPageDataSource.toSyntheticResource(context.resourceResolver(), "/test"));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Title", cardPageDataSource.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetTitleReadsHeadingTypeNotHeadingLevel() {
    properties.put("headingType", "h4");
    properties.put("headingLevel", "h6");
    resource = context.create().resource("/content/page-card-heading-type", properties);
    context.request().setResource(resource);
    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);

    assertEquals("h4", cardPageDataSource.getTitleElement().getHeadingType());
  }

  @Test
  public void testGetTitleWhenHeadingTypeInheritedFromList() {
    Map<String, Object> listProperties = new HashMap<>();
    listProperties.put("headingType", "h3");
    context.create().resource("/content/page-card-list", listProperties);

    resource = context.create().resource("/content/page-card-list/card", properties);
    context.request().setResource(resource);
    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);

    assertEquals("h3", cardPageDataSource.getTitleElement().getHeadingType());
  }

  @Test
  public void testGetTitleWhenNoHeadingTypeOnCardOrList() {
    assertEquals("h2", cardPageDataSource.getTitleElement().getHeadingType());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Description", cardPageDataSource.getDescription());
  }

  @Test
  public void testGetImageElement() throws AssetCollectionRetrievalException {
    properties.put("pagePath", "/content/page2");
    resource = context.create().resource("/content/page-card2", properties);
    context.request().setResource(resource);

    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);
    setupSamplePage("/content/page2", "/content/assets/collection/asset-1");
    setUpSampleCollection("/content/assets/collection");
    assertNotNull(cardPageDataSource.getImageElement());
  }

  @Test
  public void testGetImageElementWhenNoImage() {
    assertNull(cardPageDataSource.getImageElement());
  }

  /**
   * A card built from a page must carry the asset's own title and description. Before the fix
   * for #260, getImageElement() passed literal nulls for altText, caption and imageTitle, so an
   * author's asset title never reached the rendered card.
   */
  @Test
  public void testGetImageElementCarriesTheAssetTitleAndDescription()
          throws AssetCollectionRetrievalException {
    properties.put("pagePath", "/content/page-with-asset");
    resource = context.create().resource("/content/page-card-with-asset", properties);
    context.request().setResource(resource);

    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);
    setupSamplePage("/content/page-with-asset", "/content/assets/card-collection/asset-1");
    setUpSampleCollection("/content/assets/card-collection");

    final KestrosImage imageElement = cardPageDataSource.getImageElement();

    assertNotNull(imageElement);
    assertEquals("Asset 1 Title", imageElement.getImageTitle());
    assertEquals("Asset 1 Description", imageElement.getCaption());
    assertEquals("Asset 1 Title", imageElement.getAltText());
  }

  /**
   * An asset that cannot be resolved still renders its image - it simply loses the asset's title
   * and description. Danny, 2026-08-04: render the image, log a warning.
   */
  @Test
  public void testGetImageElementStillRendersWhenTheAssetCannotBeResolved() {
    properties.put("pagePath", "/content/page-unresolvable-asset");
    resource = context.create().resource("/content/page-card-unresolvable", properties);
    context.request().setResource(resource);

    cardPageDataSource = context.request().adaptTo(CardPageDataSource.class);
    setupSamplePage("/content/page-unresolvable-asset", "/content/assets/nowhere/asset-1");

    final KestrosImage imageElement = cardPageDataSource.getImageElement();

    assertNotNull(imageElement);
    assertEquals("/content/assets/nowhere/asset-1", imageElement.getImagePath());
    assertNull(imageElement.getImageTitle());
    assertNull(imageElement.getCaption());
  }

  @Test
  public void testGetButtonGroupElement() {
    assertNotNull(cardPageDataSource.getButtonGroupElement());
  }

  @Test
  public void testGetPage() {
    assertNotNull(cardPageDataSource.getPage());
  }

  /**
   * Every getter guards on getPage(). With no pagePath configured, and with a pagePath that does
   * not resolve, each must return null rather than throw.
   */
  private CardPageDataSource adaptWith(final Map<String, Object> props, final String name) {
    final Resource componentResource = context.create().resource("/content/" + name, props);
    context.request().setResource(componentResource);
    return context.request().adaptTo(CardPageDataSource.class);
  }

  @Test
  public void testGettersWhenThereIsNoPagePath() {
    final CardPageDataSource dataSource = adaptWith(new HashMap<>(), "card-no-path");

    assertNull(dataSource.getPage());
    assertNull(dataSource.getTitleElement());
    assertNull(dataSource.getDescription());
    assertNull(dataSource.getImageElement());
    assertNull(dataSource.getButtonGroupElement());
  }

  @Test
  public void testGettersWhenThePagePathDoesNotResolve() {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagePath", "/content/nowhere");
    final CardPageDataSource dataSource = adaptWith(props, "card-missing-page");

    assertNull(dataSource.getPage());
    assertNull(dataSource.getTitleElement());
    assertNull(dataSource.getDescription());
    assertNull(dataSource.getImageElement());
    assertNull(dataSource.getButtonGroupElement());
  }

  @Test
  public void testGetTitleElementUsesTheConfiguredHeadingLevel() {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagePath", "/content/page");
    props.put("headingLevel", "h3");

    assertNotNull(adaptWith(props, "card-h3").getTitleElement());
  }

  /** The page is resolved once and cached. */
  @Test
  public void testGetPageIsCachedAfterTheFirstLookup() {
    final Map<String, Object> props = new HashMap<>();
    props.put("pagePath", "/content/page");
    final CardPageDataSource dataSource = adaptWith(props, "card-cached");

    assertEquals(dataSource.getPage(), dataSource.getPage());
  }

  /**
   * getImageElement and getButtonGroupElement are both declared @Nullable and both already return
   * null when there is nothing to build, but an element that could not be configured was rethrown
   * as a RuntimeException, so a card that should have rendered without an image failed the render
   * outright.
   */
  @Test
  public void testElementsReturnNullRatherThanThrowWhenTheyCannotBeConfigured() throws Exception {
    when(theme.getUiFramework()).thenThrow(mock(UiFrameworkRetrievalException.class));
    final Map<String, Object> props = new HashMap<>();
    props.put("pagePath", "/content/page");
    props.put("buttonLabel", "Read more");
    final CardPageDataSource dataSource = adaptWith(props, "card-no-framework");

    assertNotNull(dataSource.getPage());
    assertNull(dataSource.getTitleElement());
    assertNull(dataSource.getImageElement());
    assertNull(dataSource.getButtonGroupElement());
  }

  /**
   * The image path is author-controlled and an exception message can carry anything, so neither
   * may be written into the warning text: a CR/LF in either would forge a log line (#686).
   */
  private List<String> warningsWhileReading(final CardPageDataSource dataSource,
          final String imagePath) {
    final Logger logger = (Logger) LoggerFactory.getLogger(CardPageDataSource.class);
    final ListAppender<ILoggingEvent> appender = new ListAppender<>();
    appender.start();
    logger.addAppender(appender);
    try {
      final CardPageDataSource.AssetText assetText = dataSource.readAssetText(imagePath);
      assertNull(assetText.getTitle());
      assertNull(assetText.getDescription());
    } finally {
      logger.detachAppender(appender);
    }
    final List<String> warnings = new ArrayList<>();
    for (final ILoggingEvent event : appender.list) {
      if (Level.WARN.equals(event.getLevel())) {
        warnings.add(event.getFormattedMessage());
      }
    }
    return warnings;
  }

  @Test
  public void testReadAssetTextWithNoAssetServiceDoesNotLogTheImagePath() {
    final List<String> warnings = warningsWhileReading(new CardPageDataSource(),
            "/content/assets/a\nFORGED");

    assertEquals(1, warnings.size());
    assertFalse(warnings.get(0).contains("FORGED"));
  }

  @Test
  public void testReadAssetTextWhenTheAssetCannotBeResolvedDoesNotLogTheImagePath() {
    final CardPageDataSource dataSource = adaptWith(new HashMap<>(), "card-forged-path");

    final List<String> warnings = warningsWhileReading(dataSource,
            "/content/assets/nowhere\nFORGED");

    assertEquals(1, warnings.size());
    assertFalse(warnings.get(0).contains("FORGED"));
  }

  @Test
  public void testReadAssetTextOnAnUnexpectedFailureDoesNotLogThePathOrMessage()
          throws Exception {
    final AssetRetrievalService failing = mock(AssetRetrievalService.class);
    when(failing.getAsset(any(), any(), any()))
            .thenThrow(new IllegalStateException("boom\nFORGED-MESSAGE"));
    final CardPageDataSource dataSource = adaptWith(new HashMap<>(), "card-failing-service");
    FieldUtils.writeField(dataSource, "assetRetrievalService", failing, true);

    final List<String> warnings = warningsWhileReading(dataSource,
            "/content/assets/a\nFORGED-PATH");

    assertEquals(1, warnings.size());
    assertFalse(warnings.get(0).contains("FORGED-PATH"));
    assertFalse(warnings.get(0).contains("FORGED-MESSAGE"));
  }
}
