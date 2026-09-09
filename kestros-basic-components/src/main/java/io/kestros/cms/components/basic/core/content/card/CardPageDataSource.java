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
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

/**
 * Datasource that builds a card from the page it points at.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardPageDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private BaseContentPage page;

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    if (getPage() != null) {
      String title = getPage().getDisplayTitle();
      String headingLevel = getResource().getValueMap().get("headingLevel", "h1");
      try {
        return new KestrosHeadingImpl(title, headingLevel,
                this,
                "title",
                "titleElement");
      } catch (ComponentConfigurationException e) {
        // do nothing.
      }
    }
    return null;
  }

  @Nullable
  @Override
  public String getDescription() {
    if (getPage() != null) {
      return getPage().getDisplayDescription();
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    if (getPage() != null) {
      if (StringUtils.isNotEmpty(getPage().getImagePath())) {
        String imagePath = getPage().getImagePath();
        String altText = null;
        String caption = null;
        String imageTitle = null;
        String href = null;
        String ariaLabel = null;
        String anchorTitle = null;
        AnchorTarget target = AnchorTarget.SAME_WINDOW;
        String id = null;
        List<ComponentVariation> componentVariations
                = getElementVariations("imageVariations",
                KestrosImage.RESOURCE_TYPE);
        String layout = getLayout("image");
        try {
          return new KestrosImageImpl(imagePath, altText, caption,
                  imageTitle, href, ariaLabel,
                  anchorTitle, target,
                  this, "image", "imageElement", assetRetrievalService);
        } catch (ComponentConfigurationException e) {
          throw new RuntimeException(e);
        }
      }
      return null;
    }
    return null;
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    if (getPage() != null) {
      try {
        List<KestrosButton> buttons = new ArrayList<>();
        String text = getResource().getValueMap().get("buttonLabel", String.class);
        String href = LinkUtils.getLink(getPage().getPath());
        String title = null;
        AnchorTarget target = AnchorTarget.SAME_WINDOW;
        String rel = null;
        String ariaLabel = null;
        String ariaDescribedBy = null;
        String lang = null;
        boolean disabled = false;
        String buttonLayout = getLayout("button");
        String buttonId = null;
        buttons.add(new KestrosButtonImpl(text, href, title,
                target, rel, ariaLabel,
                ariaDescribedBy, lang, disabled,
                this,
                "button", "buttonElement"));
        return new KestrosButtonGroupImpl(buttons,
                this,
                "buttonGroup", "buttonGroupElement");
      } catch (ComponentConfigurationException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  @Nullable
  BaseContentPage getPage() {
    if (page == null) {
      String pagePath = getResource().getValueMap().get("pagePath", String.class);
      if (pagePath != null) {
        Resource pageResource = getResourceResolver().getResource(pagePath);
        if (pageResource != null) {
          page = pageResource.adaptTo(BaseContentPage.class);
        }
      }
    }
    return page;
  }
}
