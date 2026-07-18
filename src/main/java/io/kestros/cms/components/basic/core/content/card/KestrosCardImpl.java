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

package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosHeading;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

public class KestrosCardImpl extends BaseContainerSyntheticResource implements KestrosCard {

  private KestrosHeading title;
  private String description;
  private String imagePath;
  private String layout;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;
  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  public KestrosCardImpl(BaseContentPage page, @Nullable String buttonText,
          @Nonnull BaseSlingModelDataSource dataSource,
          @Nonnull String resourcePrefix,
          @Nullable String forcedResourceName) throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);

    this.description = page.getDisplayDescription();
    try {
      this.title = new KestrosHeadingImpl(page.getDisplayTitle(), "h2",
              dataSource, "title", "titleElement");
    } catch (final ComponentConfigurationException e) {
      this.title = null;
    }
    try {
      this.image = new KestrosImageImpl(page.getImagePath(), null, null, null,
              null, null, null, AnchorTarget.SAME_WINDOW,
              dataSource,
              "image",
              "imageElement", assetRetrievalService);
    } catch (final ComponentConfigurationException e) {
      this.image = null;
    }
    try {
      if (StringUtils.isNotBlank(buttonText)) {
        List<KestrosButton> buttons = Arrays.asList(
                new KestrosButtonImpl(buttonText, LinkUtils.getLink(page.getPath()), null,
                        AnchorTarget.SAME_WINDOW, null, null, null, null, false,
                        dataSource,
                        "button", forcedResourceName));
        this.buttonGroup = new KestrosButtonGroupImpl(buttons,
                dataSource,
                "buttonGroup", "buttonGroupElement");
      } else {
        this.buttonGroup = null;
      }
    } catch (final ComponentConfigurationException e) {
      this.buttonGroup = null;
    }
  }

  public KestrosCardImpl(String description, KestrosHeading title, KestrosImage image,
          KestrosButtonGroup buttonGroup,
          @Nonnull BaseSlingModelDataSource dataSource, String resourcePrefix,
          @Nullable String forcedResourceName)
          throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.title = title;
    this.description = description;
    this.image = image;
    this.buttonGroup = buttonGroup;
  }

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    return title;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    return image;
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    return buttonGroup;
  }

}
