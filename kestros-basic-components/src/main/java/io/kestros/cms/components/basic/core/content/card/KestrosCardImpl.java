package io.kestros.cms.components.basic.core.content.card;

import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KestrosCardImpl extends BaseContainerSyntheticResource implements KestrosCard {

  private static final Logger LOG = LoggerFactory.getLogger(KestrosCardImpl.class);

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
    this(page, buttonText, dataSource, resourcePrefix, forcedResourceName, null);
  }

  /**
   * Builds a card from a page, resolving the page's image against the asset repository so the card
   * can show the asset's own title and description.
   *
   * <p>Callers that have an {@link AssetRetrievalService} should use this constructor. The
   * five-argument one delegates here with a null service, which is why cards built by callers
   * without one lose the asset's title and description.
   *
   * @param page Page to build the card from.
   * @param buttonText Text for the card's button, or null for no button.
   * @param dataSource Data source the card belongs to.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name, or null.
   * @param assetRetrievalService Service used to look up the image's asset. Null is tolerated: the
   *         image still renders, without the asset's title or description.
   * @throws ComponentConfigurationException Card could not be configured.
   */
  public KestrosCardImpl(BaseContentPage page, @Nullable String buttonText,
          @Nonnull BaseSlingModelDataSource dataSource,
          @Nonnull String resourcePrefix,
          @Nullable String forcedResourceName,
          @Nullable AssetRetrievalService assetRetrievalService)
          throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);

    if (assetRetrievalService != null) {
      this.assetRetrievalService = assetRetrievalService;
    }

    this.description = page.getDisplayDescription();
    try {
      this.title = new KestrosHeadingImpl(page.getDisplayTitle(), "h2",
              dataSource, "title", "titleElement");
    } catch (final ComponentConfigurationException e) {
      this.title = null;
    }
    final Asset asset = getAssetForPage(page);
    try {
      this.image = new KestrosImageImpl(page.getImagePath(),
              asset != null ? asset.getTitle() : null,
              asset != null ? asset.getDescription() : null,
              asset != null ? asset.getTitle() : null,
              null, null, null, AnchorTarget.SAME_WINDOW,
              dataSource,
              "image",
              "imageElement", this.assetRetrievalService);
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

  /**
   * Resolves the asset behind a page's image, so the card can carry the asset's own title and
   * description.
   *
   * <p>A card whose asset cannot be resolved still renders its image - it simply loses the title
   * and description - and the failure is logged rather than swallowed. Danny, 2026-08-04: dropping
   * the card would surprise an author more than a missing caption, and silence is what hid this
   * for months.
   *
   * @param page Page the card is built from.
   * @return The page image's asset, or null when there is no service, no image path, or the asset
   *         cannot be resolved.
   */
  @Nullable
  Asset getAssetForPage(@Nonnull final BaseContentPage page) {
    if (assetRetrievalService == null) {
      LOG.warn("Unable to resolve the asset for card image on {}. No AssetRetrievalService "
              + "available; the image renders without the asset's title or description.",
              page.getPath());
      return null;
    }
    final String imagePath = page.getImagePath();
    if (StringUtils.isBlank(imagePath)) {
      return null;
    }
    try {
      return assetRetrievalService.getAsset(imagePath, null, page.getResourceResolver());
    } catch (final AssetRetrievalException e) {
      LOG.warn("Unable to resolve asset {} for card image on {}. {} The image renders without the "
              + "asset's title or description.", imagePath, page.getPath(), e.getMessage());
      return null;
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
