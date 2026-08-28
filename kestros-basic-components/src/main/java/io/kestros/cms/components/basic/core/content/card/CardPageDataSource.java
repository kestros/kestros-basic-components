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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardPageDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private static final Logger LOG = LoggerFactory.getLogger(CardPageDataSource.class);

  private BaseContentPage page;

  @OSGiService
  @Optional
  private AssetRetrievalService assetRetrievalService;

  @Nullable
  @Override
  public KestrosHeading getTitleElement() {
    final BaseContentPage currentPage = getPage();
    if (currentPage == null) {
      return null;
    }
    String headingLevel = getResource().getValueMap().get("headingLevel", "h1");
    try {
      return new KestrosHeadingImpl(currentPage.getDisplayTitle(), headingLevel,
              this,
              "title",
              "titleElement");
    } catch (ComponentConfigurationException e) {
      LOG.warn("Unable to build the title heading for a page card; it renders without one.", e);
      return null;
    }
  }

  @Nullable
  @Override
  public String getDescription() {
    final BaseContentPage currentPage = getPage();
    return currentPage != null ? currentPage.getDisplayDescription() : null;
  }

  @Nullable
  @Override
  public KestrosImage getImageElement() {
    final BaseContentPage currentPage = getPage();
    if (currentPage == null || StringUtils.isEmpty(currentPage.getImagePath())) {
      return null;
    }
    try {
      return new KestrosImageImpl(currentPage.getImagePath(), null, null,
              null, null, null,
              null, AnchorTarget.SAME_WINDOW,
              this, "image", "imageElement", assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      // This method already returns null when the page has no image, so a card whose image cannot
      // be configured renders without one. Rethrowing as RuntimeException failed the whole render.
      LOG.warn("Unable to build the image for a page card; it renders without one.", e);
      return null;
    }
  }

  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    final BaseContentPage currentPage = getPage();
    if (currentPage == null) {
      return null;
    }
    try {
      List<KestrosButton> buttons = new ArrayList<>(1);
      String text = getResource().getValueMap().get("buttonLabel", String.class);
      buttons.add(new KestrosButtonImpl(text, LinkUtils.getLink(currentPage.getPath()), null,
              AnchorTarget.SAME_WINDOW, null, null,
              null, null, false,
              this,
              "button", "buttonElement"));
      return new KestrosButtonGroupImpl(buttons,
              this,
              "buttonGroup", "buttonGroupElement");
    } catch (ComponentConfigurationException e) {
      // Same reasoning as getImageElement: the card renders without a button rather than failing.
      LOG.warn("Unable to build the button group for a page card; it renders without one.", e);
      return null;
    }
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
