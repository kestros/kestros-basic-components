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
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
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
  /**
   * Set from the constructor, never injected: this class is not a Sling Model and is only ever
   * built with {@code new}. It previously carried {@code @OSGiService @Optional}, which looked like
   * injection and did nothing - the exact confusion this card was opened to remove.
   */
  @Nullable
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

    this.assetRetrievalService = assetRetrievalService;

    this.description = page.getDisplayDescription();
    try {
      this.title = new KestrosHeadingImpl(page.getDisplayTitle(), "h2",
              dataSource, "title", "titleElement");
    } catch (final ComponentConfigurationException e) {
      this.title = null;
    }
    final AssetText assetText = readAssetTextForPage(page);
    try {
      this.image = new KestrosImageImpl(page.getImagePath(),
              assetText.title,
              assetText.description,
              assetText.title,
              null, null, null, AnchorTarget.SAME_WINDOW,
              dataSource,
              "image",
              "imageElement", this.assetRetrievalService);
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
   * The asset's title and description, already read. Reading them is part of resolving the asset:
   * an asset that resolves but whose properties cannot be read is just as unreadable as one that
   * does not resolve, and the caller must not have to guard the getters separately.
   */
  static final class AssetText {
    final String title;
    final String description;

    AssetText(@Nullable final String title, @Nullable final String description) {
      this.title = title;
      this.description = description;
    }
  }

  /**
   * Resolves the page's image asset AND reads its title and description, with every call against
   * the asset inside the same guard.
   *
   * <p>The properties are read here rather than by the caller deliberately. When they were read at
   * the call site, an asset that resolved but threw from {@code getTitle()} escaped the
   * constructor, and {@code CardListChildPagesDataSource} turns anything escaping into a
   * RuntimeException that
   * loses the whole card list. One unreadable asset blanked the page.
   *
   * @param page Page the card is built from.
   * @return The asset's title and description, both null when there is no service, no image
   *         path, or the asset cannot be resolved or read.
   */
  @Nonnull
  final AssetText readAssetTextForPage(@Nonnull final BaseContentPage page) {
    final Asset asset = getAssetForPage(page);
    if (asset == null) {
      return new AssetText(null, null);
    }
    try {
      return new AssetText(asset.getTitle(), asset.getDescription());
    } catch (final RuntimeException e) {
      // The page path is deliberately not interpolated here. Nothing sanitises a value well enough
      // for the CRLF log-injection detector, which reports any value-bearing log argument, so the
      // page and asset are identified by the exception below instead.
      LOG.warn("Resolved the asset for a card image but could not read its properties. The image "
              + "renders without the asset's title or description.", e);
      return new AssetText(null, null);
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
  final Asset getAssetForPage(@Nonnull final BaseContentPage page) {
    final String imagePath = page.getImagePath();
    if (StringUtils.isBlank(imagePath)) {
      return null;
    }
    if (assetRetrievalService == null) {
      LOG.warn("Unable to resolve the asset for a card image. No AssetRetrievalService available; "
              + "the image renders without the asset's title or description.");
      return null;
    }
    try {
      return assetRetrievalService.getAsset(imagePath, null, page.getResourceResolver());
    } catch (final AssetRetrievalException e) {
      LOG.warn("Unable to resolve asset for a card image. The image renders without the "
              + "asset's title or description; the path is in the exception below.", e);
      return null;
    } catch (final RuntimeException e) {
      // A card that cannot resolve its asset must still render. One unreadable asset would
      // otherwise blank the page rather than drop one caption.
      LOG.warn("Unexpected failure resolving the asset for a card image. The image renders "
              + "without the asset's title or description.", e);
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
