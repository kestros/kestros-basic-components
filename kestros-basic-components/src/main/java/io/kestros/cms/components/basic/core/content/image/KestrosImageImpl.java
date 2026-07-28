package io.kestros.cms.components.basic.core.content.image;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.assets.api.exceptions.AssetRetrievalException;
import io.kestros.cms.assets.api.models.Asset;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosImageImpl extends BaseSyntheticResource implements KestrosImage {
  @OSGiService
  private ComponentVariationRetrievalService componentVariationRetrievalService;
  @OSGiService
  private ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;

  private String imagePath;
  private String altText;
  private String caption;
  private String imageTitle;
  private String href;
  private String ariaLabel;
  private String anchorTitle;
  private AnchorTarget target;
  private AssetRetrievalService assetRetrievalService;

  public KestrosImageImpl(String imagePath,
          String altText, String caption,
          String imageTitle,
          String href, String ariaLabel,
          String anchorTitle, AnchorTarget target,
          @Nonnull BaseSlingModelDataSource dataSource,
          String resourcePrefix,
          String forcedResourceName, AssetRetrievalService assetRetrievalService) throws
          ComponentConfigurationException {
    super(dataSource, resourcePrefix,
            forcedResourceName);
    this.assetRetrievalService = assetRetrievalService;
    this.imagePath = imagePath;
    this.altText = altText;
    this.caption = caption;
    this.imageTitle = imageTitle;
    this.href = href;
    this.ariaLabel = ariaLabel;
    this.anchorTitle = anchorTitle;
    this.target = target;
    if (StringUtils.isEmpty(this.imagePath)) {
      throw new ComponentConfigurationException("Missing required property");
    }
  }

  public KestrosImageImpl(Resource resource,
          @Nonnull BaseSlingModelDataSource dataSource,
          String resourcePrefix,
          String forcedResourceName, AssetRetrievalService assetRetrievalService) throws
          ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.assetRetrievalService = assetRetrievalService;
    String assetPath = resource.getValueMap().get("imagePath", String.class);
    if(LinkUtils.isLinkExternal(assetPath)) {
      try {
        Asset asset = getAsset(assetPath, resource.getResourceResolver());
        this.imagePath = asset.getPath();
      } catch (AssetRetrievalException e) {
        throw new ComponentConfigurationException("Could not retrieve asset for path: " + assetPath,
                e);
      }
    } else {
      this.imagePath = assetPath;
    }

    this.altText = resource.getValueMap().get("altText", String.class);
    this.caption = resource.getValueMap().get("caption", String.class);
    this.imageTitle = resource.getValueMap().get("imageTitle", String.class);
    this.href = resource.getValueMap().get("href", String.class);
    this.ariaLabel = resource.getValueMap().get("ariaLabel", String.class);
    this.anchorTitle = resource.getValueMap().get("anchorTitle", String.class);
    this.target = AnchorTarget.lookup(resource);
    if (StringUtils.isEmpty(this.imagePath)) {
      throw new ComponentConfigurationException("Missing required property");
    }
  }

  @Nonnull
  Asset getAsset(String path, ResourceResolver resourceResolver) throws AssetRetrievalException {
    if (assetRetrievalService == null) {
      throw new AssetRetrievalException("AssetRetrievalService is not available.");
    }
    return assetRetrievalService.getAsset(path, null, resourceResolver);
  }

  @Override
  @Nonnull
  public String getImageTitle() {
    return imageTitle;
  }

  @Nonnull
  @Override
  public String getImagePath() {
    return imagePath;
  }

  @Nullable
  @Override
  public String getHref() {
    return href;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return ariaLabel;
  }

  @Nullable
  @Override
  public String getAnchorTitle() {
    return anchorTitle;
  }

  @Nullable
  @Override
  public String getRel() {
    return "";
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return "";
  }

  @Nullable
  @Override
  public String getLang() {
    return "";
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return target;
  }


  @Nullable
  @Override
  public String getCaption() {
    return caption;
  }

  @Nonnull
  @Override
  public String getAltText() {
    return altText;
  }

  @JsonIgnore
  @Nonnull
  @Override
  public ComponentVariationRetrievalService getComponentVariationRetrievalService() {
    return componentVariationRetrievalService;
  }

  @JsonIgnore
  @Nonnull
  @Override
  public ComponentUiFrameworkViewRetrievalService getComponentUiFrameworkViewRetrievalService() {
    return componentUiFrameworkViewRetrievalService;
  }
}
