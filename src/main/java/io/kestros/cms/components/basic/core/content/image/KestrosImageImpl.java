package io.kestros.cms.components.basic.core.content.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.content.BaseSyntheticResource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

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

  public KestrosImageImpl(String imagePath,
          String altText, String caption,
          String imageTitle,
          String href, String ariaLabel,
          String anchorTitle, AnchorTarget target,
          ResourceResolver resourceResolver, UiFramework uiFramework, String parentPath,
          List<ComponentVariation> componentVariations, String layout, String id,
          String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
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

  public KestrosImageImpl(Resource resource, ResourceResolver resourceResolver,
          UiFramework uiFramework, String parentPath,
          List<ComponentVariation> componentVariations, String layout,
          String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout,
            resource.getValueMap().get("id", String.class), forcedResourceName);
    this.imagePath = resource.getValueMap().get("assetPath", String.class);
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

  @Override
  public String getImageTitle() {
    return imageTitle;
  }

  @Nullable
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

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return target;
  }

  @Nullable
  @Override
  public String getAltText() {
    return altText;
  }

  @Nullable
  @Override
  public String getCaption() {
    return caption;
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
