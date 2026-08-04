package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosCardImplTest extends BaseSyntheticTest {
  private KestrosCardImpl card;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;
  private KestrosHeading title;


  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
            CardListStaticDataSource.class);

    List<ComponentVariation> variations = new ArrayList<>();
    title = new KestrosHeadingImpl(
            "Title",
            "h2",
            dataSource,
            "title",
            "titleElement"
    );
    image = new KestrosImageImpl(
            "/imagePath.jpg",
            "Title Text",
            "http://example.com/image.jpg",
            null,
            null,
            null, null,
            AnchorTarget.SAME_WINDOW,
            dataSource,
            "image",
            "imageElement",
            assetRetrievalService
    );
    buttonGroup = new KestrosButtonGroupImpl(new ArrayList<>(),
            dataSource,
            "buttonGroup",
            "buttonGroupElement"
    );
    card = new KestrosCardImpl(
            "Description",
            title,
            image,
            buttonGroup,
            dataSource,
            "card",
            "forcedResourceName"
    );
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = card.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/forcedResourceName", syntheticResource.getPath());
    assertEquals("Description", syntheticResource.getValueMap().get("description", String.class));

    context.request().setResource(syntheticResource);
    CardStaticDataSource cardStaticDataSource = context.request().adaptTo(
            CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource);
    assertEquals("Description", cardStaticDataSource.getDescription());
    assertEquals("/imagePath.jpg",
            cardStaticDataSource.getImage().getValueMap().get("imagePath", String.class));
    assertEquals("Title",
            cardStaticDataSource.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Title", card.getTitle().getValueMap().get("headingText", String.class));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Description", card.getDescription());
  }

  @Test
  public void testGetImageElement() {
    assertNotNull(card.getImageElement());
  }

  @Test
  public void testGetButtonGroupElement() {
    assertNotNull(card.getButtonGroupElement());
  }

  private CardListStaticDataSource newDataSource() {
    Resource resource = context.create().resource("/content/page-card-parent");
    context.currentResource(resource);
    return context.request().adaptTo(CardListStaticDataSource.class);
  }

  /**
   * A real adapted page rather than a bare mock: BaseResource.getResourceResolver() is final, so a
   * mock NPEs the moment the card asks it for a resolver.
   */
  private BaseContentPage pageWithImage() {
    Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    Resource pageResource = context.create().resource("/content/pages/page", pageProperties);
    BaseContentPage page = spy(pageResource.adaptTo(BaseContentPage.class));
    doReturn("Page Title").when(page).getDisplayTitle();
    doReturn("Page Description").when(page).getDisplayDescription();
    doReturn("/content/assets/photo.jpg").when(page).getImagePath();
    return page;
  }

  private ListAppender<ILoggingEvent> attachAppenderToCardLogger() {
    ch.qos.logback.classic.Logger logger
            = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(
            KestrosCardImpl.class);
    logger.setLevel(Level.TRACE);
    ListAppender<ILoggingEvent> appender = new ListAppender<>();
    appender.start();
    logger.addAppender(appender);
    return appender;
  }

  @Test
  public void testCardBuiltFromPageShowsTheAssetsTitleAndDescription() throws Exception {
    Asset asset = mock(Asset.class);
    when(asset.getTitle()).thenReturn("Asset Title");
    when(asset.getDescription()).thenReturn("Asset Description");
    AssetRetrievalService service = mock(AssetRetrievalService.class);
    when(service.getAsset("/content/assets/photo.jpg", null,
            context.resourceResolver())).thenReturn(asset);

    KestrosCardImpl pageCard = new KestrosCardImpl(pageWithImage(), "Read more", newDataSource(), "card",
            "pageCard", service);

    KestrosImage cardImage = pageCard.getImageElement();
    assertNotNull(cardImage);
    assertEquals("Asset Title", cardImage.getImageTitle());
    assertEquals("Asset Title", cardImage.getAltText());
    assertEquals("Asset Description", cardImage.getCaption());
  }

  @Test
  public void testCardBuiltFromPageStillRendersTheImageWhenTheAssetCannotBeResolved()
          throws Exception {
    ListAppender<ILoggingEvent> appender = attachAppenderToCardLogger();
    AssetRetrievalService service = mock(AssetRetrievalService.class);
    when(service.getAsset("/content/assets/photo.jpg", null,
            context.resourceResolver())).thenThrow(new AssetRetrievalException("no such asset"));

    KestrosCardImpl pageCard = new KestrosCardImpl(pageWithImage(), "Read more", newDataSource(), "card",
            "pageCard", service);

    KestrosImage cardImage = pageCard.getImageElement();
    assertNotNull("the image must still render when the asset cannot be resolved", cardImage);
    assertEquals("/content/assets/photo.jpg", cardImage.getImagePath());
    assertNull(cardImage.getImageTitle());

    assertTrue("an unresolvable asset must be logged, not swallowed",
            appender.list.stream().anyMatch(
                    event -> Level.WARN.equals(event.getLevel()) && event.getMessage().contains(
                            "Unable to resolve asset")));
  }

  @Test
  public void testCardBuiltFromPageWarnsWhenThereIsNoAssetRetrievalService() throws Exception {
    ListAppender<ILoggingEvent> appender = attachAppenderToCardLogger();

    KestrosCardImpl pageCard = new KestrosCardImpl(pageWithImage(), "Read more", newDataSource(), "card",
            "pageCard");

    assertNotNull(pageCard.getImageElement());
    assertTrue("a missing AssetRetrievalService must be logged, not silently ignored",
            appender.list.stream().anyMatch(
                    event -> Level.WARN.equals(event.getLevel()) && event.getMessage().contains(
                            "No AssetRetrievalService")));
  }

}