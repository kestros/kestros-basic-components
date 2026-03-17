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

import io.kestros.cms.components.basic.api.content.KestrosVideoEmbed;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class VideoEmbedDataSourceComponent extends BaseDataSourceComponent<KestrosVideoEmbed>
    implements KestrosVideoEmbed {

  @Nullable
  @Override
  public String getVideoEmbedCode() {
    String embedCode = getComponentData().getVideoEmbedCode();
    if (embedCode == null) {
      return null;
    }
    // Defense-in-depth: sanitize embed code before returning to HTL.
    // Even though datasources should return safe HTML, this guard protects
    // against future datasource variants that may not validate as rigorously.
    return EmbedSanitizer.sanitize(embedCode);
  }
}
