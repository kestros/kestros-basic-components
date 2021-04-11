package io.kestros.cms.components.basic.content.image;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/image-component")
public class ImageComponent extends BaseComponent {

  /**
   * Image path.
   *
   * @return Image path.
   */
  @KestrosProperty(description = "Image path.")
  public String getImage() {
    try {
      return SlingModelUtils.getChildAsBaseResource("image", this).getPath();
    } catch (ChildResourceNotFoundException e) {
      return StringUtils.EMPTY;
    }
  }

  public String getAltText() {
    return getProperty("altText", StringUtils.EMPTY);
  }

}
