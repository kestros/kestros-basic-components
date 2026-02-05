package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import java.util.ArrayList;
import java.util.List;
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
        "imageElement"
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
    assertEquals("Title", cardStaticDataSource.getTitle().getValueMap().get("text", String.class));
  }

  @Test
  public void testGetTitle() {
    assertEquals("Title", card.getTitle().getValueMap().get("text", String.class));
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

}