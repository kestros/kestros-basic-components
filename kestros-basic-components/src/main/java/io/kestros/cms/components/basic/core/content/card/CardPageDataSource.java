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
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.components.basic.core.content.buttongroup.KestrosButtonGroupImpl;
import io.kestros.cms.components.basic.core.content.heading.KestrosHeadingImpl;
import io.kestros.cms.components.basic.core.content.image.KestrosImageImpl;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
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
    String headingLevel = CardHeadingType.resolve(getResource());
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
    final String imagePath = currentPage.getImagePath();
    final AssetText assetText = readAssetText(imagePath);
    try {
      return new KestrosImageImpl(imagePath, StringUtils.defaultString(assetText.title),
              assetText.description, assetText.title,
              null, null, null, AnchorTarget.SAME_WINDOW,
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

  /**
   * The asset's own title and description, both null when the asset cannot be resolved or read.
   */
  static final class AssetText {

    private final String title;
    private final String description;

    AssetText(@Nullable final String title, @Nullable final String description) {
      this.title = title;
      this.description = description;
    }
  }

  /**
   * Resolves the asset behind the page's image and reads its title and description.
   *
   * <p>Every call against the asset happens inside the guard: an asset that resolves but throws
   * from getTitle() must not escape, because CardListChildPagesDataSource turns anything escaping
   * into a RuntimeException that loses the whole card list. One unreadable asset would blank the
   * page rather than drop one caption.
   *
   * <p>A card whose asset cannot be resolved still renders its image and logs a warning. Danny,
   * 2026-08-04: dropping the card would surprise an author more than a missing caption, and
   * silence is what hid this for months.
   *
   * @param imagePath Path of the page's image.
   * @return The asset's title and description, both null when there is no service or the asset
   *         cannot be resolved or read.
   */
  @Nonnull
  AssetText readAssetText(@Nonnull final String imagePath) {
    if (assetRetrievalService == null) {
      LOG.warn("Unable to resolve the asset for card image {}. No AssetRetrievalService "
              + "available; the image renders without the asset's title or description.",
              imagePath);
      return new AssetText(null, null);
    }
    try {
      final Asset asset = assetRetrievalService.getAsset(imagePath, null, getResourceResolver());
      return new AssetText(asset.getTitle(), asset.getDescription());
    } catch (final AssetRetrievalException e) {
      LOG.warn("Unable to resolve asset {} for card image. {} The image renders without the "
              + "asset's title or description.", imagePath, e.getMessage(), e);
      return new AssetText(null, null);
    } catch (final RuntimeException e) {
      LOG.warn("Unexpected failure reading asset {} for card image. {}: {} The image renders "
              + "without the asset's title or description.", imagePath,
              e.getClass().getSimpleName(), e.getMessage(), e);
      return new AssetText(null, null);
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
