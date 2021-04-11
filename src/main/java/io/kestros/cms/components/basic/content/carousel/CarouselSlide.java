package io.kestros.cms.components.basic.content.carousel;

import io.kestros.cms.components.basic.content.image.ImageComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/carousel-slide")
public class CarouselSlide extends ImageComponent {


  public String getCaption() {
    return getProperty("caption", StringUtils.EMPTY);
  }

}
