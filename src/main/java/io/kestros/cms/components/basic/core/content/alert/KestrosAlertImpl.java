package io.kestros.cms.components.basic.core.content.alert;

import io.kestros.cms.components.basic.api.content.KestrosAlert;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.ResourceResolver;

public class KestrosAlertImpl extends BaseSyntheticResource implements KestrosAlert {

  private String heading;
  private String text;

  public KestrosAlertImpl(String heading, String text, ResourceResolver resourceResolver,
          UiFramework uiFramework,
          String parentPath,
          List<ComponentVariation> componentVariations, String layout, String id,
          String forcedResourceName) throws
          ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
    this.heading = heading;
    this.text = text;
  }

  @Nullable
  @Override
  public String getHeading() {
    return heading;
  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }
}
