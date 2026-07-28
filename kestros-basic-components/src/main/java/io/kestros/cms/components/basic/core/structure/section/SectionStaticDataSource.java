package io.kestros.cms.components.basic.core.structure.section;

import io.kestros.cms.components.basic.api.KestrosBasicComponentElement;
import io.kestros.cms.components.basic.api.structure.KestrosSection;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Supplies a {@link KestrosSection} from properties authored on the component's own resource.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class SectionStaticDataSource extends BaseContainerSlingModelDataSource
    implements KestrosSection {

  @Nullable
  @Override
  public String getBackgroundImage() {
    // todo look up asset.
    return getResource().getValueMap().get("backgroundImage", String.class);
  }

  @Nonnull
  @Override
  public List<KestrosBasicComponentElement> getChildElements() {
    return List.of();
  }
}
