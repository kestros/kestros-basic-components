/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.content.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosImageImplTest extends BaseSyntheticTest {

  private KestrosImageImpl image;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    image = new KestrosImageImpl(
        "/content/assets/image.jpg",
        "Alt text",
        "Caption text",
        "Image Title",
        "/content/page.html",
        "Aria Label",
        "Anchor Title",
        AnchorTarget.SAME_WINDOW,
        dataSource,
        "image",
        "imageElement",
        assetRetrievalService
    );
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = image.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/imageElement", syntheticResource.getPath());
  }

  @Test
  public void testGetImagePath() {
    assertEquals("/content/assets/image.jpg", image.getImagePath());
  }

  @Test
  public void testGetAltText() {
    assertEquals("Alt text", image.getAltText());
  }

  @Test
  public void testGetCaption() {
    assertEquals("Caption text", image.getCaption());
  }

  @Test
  public void testGetImageTitle() {
    assertEquals("Image Title", image.getImageTitle());
  }

  @Test
  public void testGetHref() {
    assertEquals("/content/page.html", image.getHref());
  }

  @Test
  public void testGetAriaLabel() {
    assertEquals("Aria Label", image.getAriaLabel());
  }

  @Test
  public void testGetAnchorTitle() {
    assertEquals("Anchor Title", image.getAnchorTitle());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, image.getTarget());
  }

  @Test(expected = ComponentConfigurationException.class)
  public void testConstructorThrowsWhenImagePathEmpty() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent2");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    new KestrosImageImpl("", "Alt text", null, null, null, null, null,
        AnchorTarget.SAME_WINDOW, dataSource, "image", "failElement", assetRetrievalService);
  }
}
