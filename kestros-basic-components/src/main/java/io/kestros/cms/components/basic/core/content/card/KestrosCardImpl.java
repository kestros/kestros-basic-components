package io.kestros.cms.components.basic.core.content.card;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

/**
 * Programmatic {@link KestrosCard}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosCardImpl extends BaseContainerSyntheticResource implements KestrosCard {

  private KestrosHeading title;
  private String description;
  private KestrosImage image;
  private KestrosButtonGroup buttonGroup;

  /**
   * Constructs a card impl.
   *
   * @param page Page.
   * @param buttonText Button text.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
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
              // No asset retrieval service: this class is built with new, never adapted, so
              // the @OSGiService field it used to read was always null. Passing null
              // explicitly rather than through a field that looked injected. A card image
              // built from a page therefore does not resolve the asset's own title or
              // description - see the PR note.
              "imageElement", null);
    } catch (final ComponentConfigurationException e) {
      this.image = null;
    }
    try {
      if (StringUtils.isNotBlank(buttonText)) {
        List<KestrosButton> buttons = Collections.singletonList(
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

  /**
   * Constructs a card impl.
   *
   * @param description Description.
   * @param title Title.
   * @param image Image.
   * @param buttonGroup Button group.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
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
