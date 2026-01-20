package io.kestros.cms.components.basic.core.content.image;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosImage;
import io.kestros.cms.components.basic.core.BaseDataSourceComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ImageDataSourceComponent extends BaseDataSourceComponent<KestrosImage> implements
        KestrosImage {

  @Override
  public String getImageTitle() {
    return getComponentData().getImageTitle();
  }

  @Nullable
  @Override
  public String getImagePath() {
    return getComponentData().getImagePath();
  }

  @Nullable
  @Override
  public String getHref() {
    return getComponentData().getHref();
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return getComponentData().getAriaLabel();
  }

  @Nullable
  @Override
  public String getAnchorTitle() {
    return getComponentData().getAnchorTitle();
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return getComponentData().getTarget();
  }

  @Nullable
  @Override
  public String getAltText() {
    return getComponentData().getAltText();
  }

  @Nullable
  @Override
  public String getCaption() {
    return getComponentData().getCaption();
  }
}
