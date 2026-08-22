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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.exceptions.ComponentElementRenderingException;

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

  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed ComponentElementRenderingException so the failure"
          + " stays identifiable, per the ruling on DataSourceComponent.")
  @Nullable
  @Override
  public KestrosImage getImageElement() {
    final BaseContentPage cardPage = getPage();
    if (cardPage == null || StringUtils.isEmpty(cardPage.getImagePath())) {
      return null;
    }
    try {
      // Arguments after the path are altText, caption, imageTitle, href, ariaLabel and
      // anchorTitle. They were locals initialised to null and passed straight through; the
      // variations and layout locals alongside them were computed and never used at all.
      return new KestrosImageImpl(cardPage.getImagePath(), null, null,
              null, null, null,
              null, AnchorTarget.SAME_WINDOW,
              this, "image", "imageElement", assetRetrievalService);
    } catch (ComponentConfigurationException e) {
      throw new ComponentElementRenderingException(
              "Unable to build the image element for the card at " + cardPage.getPath() + ".", e);
    }
  }

  @SuppressFBWarnings(value = "EXS_EXCEPTION_SOFTENING_NO_CHECKED",
      justification = "Called from HTL, which cannot handle a checked exception. The checked"
          + " cause is wrapped in a typed ComponentElementRenderingException so the failure"
          + " stays identifiable, per the ruling on DataSourceComponent.")
  @Nullable
  @Override
  public KestrosButtonGroup getButtonGroupElement() {
    final BaseContentPage cardPage = getPage();
    if (cardPage == null) {
      return null;
    }
    try {
      final List<KestrosButton> buttons = new ArrayList<>(1);
      // Arguments after the href are title, target, rel, ariaLabel, ariaDescribedBy, lang and
      // disabled. All but the target were locals initialised to null and passed straight through;
      // the layout and id locals alongside them were computed and never used at all.
      buttons.add(new KestrosButtonImpl(
              getResource().getValueMap().get("buttonLabel", String.class),
              LinkUtils.getLink(cardPage.getPath()), null,
              AnchorTarget.SAME_WINDOW, null, null,
              null, null, false,
              this,
              "button", "buttonElement"));
      return new KestrosButtonGroupImpl(buttons,
              this,
              "buttonGroup", "buttonGroupElement");
    } catch (ComponentConfigurationException e) {
      throw new ComponentElementRenderingException(
              "Unable to build the button group for the card at " + cardPage.getPath() + ".", e);
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
