package io.kestros.cms.components.basic.core.structure.section;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.structure.KestrosSection;
import io.kestros.cms.components.basic.core.BaseContainerDataSourceComponent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class SectionDataSourceComponent
    extends BaseContainerDataSourceComponent<KestrosSection>
    implements KestrosSection {


  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return getComponentData().getChildElements();
  }

  @Nullable
  @Override
  public String getBackgroundImage() {
    return getComponentData().getBackgroundImage();
  }
}
