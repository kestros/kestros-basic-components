package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.content.KestrosCard;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
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

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CardPageDataSource extends BaseContainerSlingModelDataSource implements KestrosCard {

  private BaseContentPage page;

  @Nullable
  @Override
  public String getTitle() {
    if (getPage() != null) {
      return getPage().getDisplayTitle();
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
                = KestrosBasicComponentElement.getAppliedVariations("imageVariations",
                getResource(), KestrosImage.RESOURCE_TYPE,
                getUiFramework(), getComponentVariationRetrievalService(),
                getComponentUiFrameworkViewRetrievalService());
        String layout = KestrosBasicComponentElement.getLayout("imageLayout",
                getResource());
        try {
          return new KestrosImageImpl(imagePath, altText, caption,
                  imageTitle, href, ariaLabel,
                  anchorTitle, target, getResourceResolver(),
                  getUiFramework(), getPath(), componentVariations,
                  layout, id, "imageElement");
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
        String buttonLayout = KestrosBasicComponentElement.getLayout("buttonLayout",
                getResource());
        String buttonId = null;
        buttons.add(new KestrosButtonImpl(text, href, title,
                target, rel, ariaLabel,
                ariaDescribedBy, lang, disabled,
                getResourceResolver(), getUiFramework(),
                getPath(),
                KestrosBasicComponentElement.getAppliedVariations("buttonVariations",
                        getResource(),
                        KestrosButton.RESOURCE_TYPE,
                        getUiFramework(),
                        getComponentVariationRetrievalService(),
                        getComponentUiFrameworkViewRetrievalService()),
                buttonLayout, buttonId, "buttonGroup"));
        List<ComponentVariation> buttonVariations
                = KestrosBasicComponentElement.getAppliedVariations(
                "buttonVariations",
                getResource(),
                KestrosButton.RESOURCE_TYPE,
                getUiFramework(),
                getComponentVariationRetrievalService(),
                getComponentUiFrameworkViewRetrievalService());
        List<ComponentVariation> componentVariations
                = KestrosBasicComponentElement.getAppliedVariations("buttonGroupVariations",
                getResource(),
                KestrosButtonGroup.RESOURCE_TYPE,
                getUiFramework(),
                getComponentVariationRetrievalService(),
                getComponentUiFrameworkViewRetrievalService());
        String buttonGroupLayout = KestrosBasicComponentElement.getLayout("buttonGroupLayout",
                getResource());
        String id = null;
        return new KestrosButtonGroupImpl(buttons,
                buttonVariations,
                getResourceResolver(), getUiFramework(),
                getPath(),
                componentVariations,
                buttonGroupLayout, id, "buttonGroup");
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
