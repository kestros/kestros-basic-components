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
package io.kestros.cms.components.basic.core.content.link;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosLinkImplTest extends BaseSyntheticTest {

  private KestrosLinkImpl link;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(CardListStaticDataSource.class);

    link = new KestrosLinkImpl("Link Text", "/content/page.html", "Link Title",
        AnchorTarget.SAME_WINDOW, "noopener", "Aria Label", "aria-describedby", "en",
        dataSource, "link", "linkElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = link.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/linkElement", syntheticResource.getPath());
  }

  @Test
  public void testGetText() {
    assertEquals("Link Text", link.getText());
  }

  @Test
  public void testGetHref() {
    assertEquals("/content/page.html", link.getHref());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Link Title", link.getTitle());
  }

  @Test
  public void testGetTarget() {
    assertEquals(AnchorTarget.SAME_WINDOW, link.getTarget());
  }

  @Test
  public void testGetRel() {
    assertEquals("noopener", link.getRel());
  }

  @Test
  public void testGetAriaLabel() {
    assertEquals("Aria Label", link.getAriaLabel());
  }

  @Test
  public void testGetAriaDescribedBy() {
    assertEquals("aria-describedby", link.getAriaDescribedBy());
  }

  @Test
  public void testGetLang() {
    assertEquals("en", link.getLang());
  }

  /**
   * The page constructor used to assign six fields to themselves, so title, target, rel and the
   * aria attributes were silently always null. The assignments are gone; this asserts what the
   * constructor actually provides, so that changing it is a deliberate act rather than a surprise.
   */
  @Test
  public void testPageConstructorSetsOnlyTheTextAndHref() throws ComponentConfigurationException {
    final Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    final Map<String, Object> contentProperties = new HashMap<>();
    contentProperties.put("jcr:primaryType", "nt:unstructured");
    contentProperties.put("jcr:title", "A Page Title");
    context.create().resource("/content/sample-page", pageProperties);
    context.create().resource("/content/sample-page/jcr:content", contentProperties);

    final BaseContentPage page = context.resourceResolver().getResource("/content/sample-page")
        .adaptTo(BaseContentPage.class);
    final Resource parent = context.create().resource("/content/link-parent");
    context.currentResource(parent);
    final CardListStaticDataSource dataSource =
        context.request().adaptTo(CardListStaticDataSource.class);

    final KestrosLinkImpl pageLink =
        new KestrosLinkImpl(page, dataSource, "link", "linkElement");

    assertEquals("A Page Title", pageLink.getText());
    assertNotNull(pageLink.getHref());
    assertNull(pageLink.getTitle());
    assertNull(pageLink.getTarget());
    assertNull(pageLink.getRel());
    assertNull(pageLink.getAriaLabel());
    assertNull(pageLink.getAriaDescribedBy());
    assertNull(pageLink.getLang());
  }
}
