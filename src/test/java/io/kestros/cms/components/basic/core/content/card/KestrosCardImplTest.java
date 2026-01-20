package io.kestros.cms.components.basic.core.content.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosCardImplTest extends BaseSyntheticTest {
  private KestrosCardImpl card;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;


  @Override
  public void setupElement() throws ComponentConfigurationException {
    List<ComponentVariation> variations = new ArrayList<>();
    image = new KestrosImageImpl(
            "/imagePath.jpg",
            "Title Text",
            "http://example.com/image.jpg",
            null,
            null,
            null, null,
            AnchorTarget.SAME_WINDOW,
            getResourceResolver(),
            getUiFramework(),
            "/parent/image",
            variations,
            "default",
            "imageId",
            "imageElement"
    );
    buttonGroup = new KestrosButtonGroupImpl(new ArrayList<>(), new ArrayList<>(),
            getResourceResolver(),
            getUiFramework(),
            "/parent/buttonGroup",
            variations,
            "default",
            "buttonGroupId",
            "buttonGroupElement"
    );
    card = new KestrosCardImpl(
            "Title",
            "Description",
            image,
            buttonGroup,
            getResourceResolver(),
            getUiFramework(),
            "/parent",
            variations,
            "default",
            "id",
            "forcedResourceName"
    );
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = card.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/forcedResourceName", syntheticResource.getPath());
    assertEquals("Title", syntheticResource.getValueMap().get("title", String.class));
    assertEquals("Description", syntheticResource.getValueMap().get("description", String.class));
    context.request().setResource(syntheticResource);
    CardStaticDataSource cardStaticDataSource = context.request().adaptTo(
            CardStaticDataSource.class);
    assertNotNull(cardStaticDataSource);
    assertEquals("Title", cardStaticDataSource.getTitle());
    assertEquals("Description", cardStaticDataSource.getDescription());
    assertEquals("/imagePath.jpg",cardStaticDataSource.getImage().getValueMap().get("imagePath", String.class));

  }

  @Test
  public void testGetTitle() {
    assertEquals("Title", card.getTitle());
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