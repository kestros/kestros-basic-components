package io.kestros.cms.components.basic.content.carousel;

import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import io.kestros.commons.structuredslingmodels.utils.SlingModelUtils;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,
       resourceType = "kestros/commons/components/content/carousel")
public class CarouselComponent extends BaseComponent {

  public BaseResource getSlidesRootResource() throws ChildResourceNotFoundException {
    return SlingModelUtils.getChildAsBaseResource("slides", this);
  }

  public List<CarouselSlide> getSlides() {
    try {
      return SlingModelUtils.getChildrenOfType(getSlidesRootResource(), CarouselSlide.class);
    } catch (ChildResourceNotFoundException e) {
      //      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  public String getPreviousText() {
    return getProperty("previousText", StringUtils.EMPTY);
  }

  public String getNextText() {
    return getProperty("nextText", StringUtils.EMPTY);
  }

  public Boolean isIncludeIndicators() {
    return getProperty("includeIndicators", Boolean.FALSE);
  }

  public Boolean isIncludeControls() {
    return getProperty("includeControls", Boolean.FALSE);
  }
}
