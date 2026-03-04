package io.kestros.cms.components.basic.core.content.buttongroup;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.core.BaseContainerSlingModelDataSource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import io.kestros.commons.structuredslingmodels.exceptions.NoValidAncestorException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Datasource that performs an inverse lookup — queries session pages where the
 * current presenter is referenced, and returns buttons linking to each session.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class ButtonGroupPresenterSessionsDataSource extends BaseContainerSlingModelDataSource
    implements KestrosButtonGroup {

  @Nonnull
  @Override
  public List<KestrosButton> getButtonsElements() {
    List<KestrosButton> buttons = new ArrayList<>();
    BaseContentPage currentPage = getCurrentOrContainingPage();
    if (currentPage == null) {
      return buttons;
    }

    String sessionsPath = getResource().getValueMap().get("sessionsPath", String.class);
    if (sessionsPath == null) {
      return buttons;
    }

    Resource sessionsRoot = getResourceResolver().getResource(sessionsPath);
    if (sessionsRoot == null) {
      return buttons;
    }

    String presenterPath = currentPage.getPath();

    for (Resource sessionResource : sessionsRoot.getChildren()) {
      BaseContentPage sessionPage = sessionResource.adaptTo(BaseContentPage.class);
      if (sessionPage == null) {
        continue;
      }

      Resource contentResource = sessionResource.getChild("jcr:content");
      if (contentResource == null) {
        continue;
      }

      String presenterRef = contentResource.getValueMap().get("presenterPath", String.class);
      String[] presenterRefs = contentResource.getValueMap().get("presenterPaths", String[].class);

      boolean matches = false;
      if (presenterRef != null && presenterRef.equals(presenterPath)) {
        matches = true;
      }
      if (presenterRefs != null) {
        for (String ref : presenterRefs) {
          if (ref.equals(presenterPath)) {
            matches = true;
            break;
          }
        }
      }

      if (matches) {
        try {
          buttons.add(new KestrosButtonImpl(
              sessionPage.getDisplayTitle(),
              LinkUtils.getLink(sessionPage.getPath()),
              null,
              AnchorTarget.SAME_WINDOW,
              null, null, null, null, false,
              this,
              "button",
              sessionPage.getName()));
        } catch (Exception e) {
          // Skip buttons that fail to construct
        }
      }
    }

    return new ArrayList<>(buttons);
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getButtonVariations() {
    return new ArrayList<>();
  }
}
