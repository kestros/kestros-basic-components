package io.kestros.cms.components.basic.core.content.card;

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
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class KestrosCardImpl extends BaseContainerSyntheticResource implements KestrosCard {

  private KestrosHeading title;
  private String description;
  private String imagePath;
  private String layout;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;

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
          "imageElement");
    } catch (final ComponentConfigurationException e) {
      this.image = null;
    }
    try {
      if(StringUtils.isNotBlank(buttonText)) {
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
