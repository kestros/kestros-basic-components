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
package io.kestros.cms.components.basic.core.content.videoembed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticTest;
import io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class KestrosVideoEmbedImplTest extends BaseSyntheticTest {

  private KestrosVideoEmbedImpl videoEmbed;

  @Override
  public void setupElement() throws ComponentConfigurationException {
    Resource resource = context.create().resource("/content/parent");
    context.currentResource(resource);
    CardListStaticDataSource dataSource = context.request().adaptTo(
        CardListStaticDataSource.class);

    videoEmbed = new KestrosVideoEmbedImpl(
        "<iframe src='https://www.youtube.com/embed/abc123'></iframe>",
        dataSource, "videoembed", "videoEmbedElement");
  }

  @Override
  public void testToSyntheticResource() {
    Resource syntheticResource = videoEmbed.toSyntheticResource(getResourceResolver(), "/parent");
    assertNotNull(syntheticResource);
    assertEquals("/synthetics/parent/videoEmbedElement", syntheticResource.getPath());
  }

  @Test
  public void testGetVideoEmbedCode() {
    assertEquals("<iframe src='https://www.youtube.com/embed/abc123'></iframe>",
        videoEmbed.getVideoEmbedCode());
  }
}
