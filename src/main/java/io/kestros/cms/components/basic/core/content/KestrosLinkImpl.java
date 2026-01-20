package io.kestros.cms.components.basic.core.content;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.sling.api.resource.ResourceResolver;

public class KestrosLinkImpl extends BaseSyntheticResource implements KestrosLink {

  private String text;
  private String href;
  private String title;
  private AnchorTarget target;
  private String rel;
  private String ariaLabel;
  private String ariaDescribedBy;
  private String lang;


  public KestrosLinkImpl(String text, String href, String title, AnchorTarget target, String rel,
          String ariaLabel, String ariaDescribedBy, String lang, ResourceResolver resourceResolver,
          UiFramework uiFramework, String parentPath,
          List<ComponentVariation> componentVariations, String layout, String id,
          String forcedResourceName)
          throws ComponentConfigurationException {
    super(resourceResolver, uiFramework, parentPath, componentVariations, layout, id,
            forcedResourceName);
    this.text = text;
    this.href = href;
    this.title = title;
    this.target = target;
    this.rel = rel;
    this.ariaLabel = ariaLabel;
    this.ariaDescribedBy = ariaDescribedBy;
    this.lang = lang;

  }

  @Nullable
  @Override
  public String getText() {
    return text;
  }

  @Nullable
  @Override
  public String getHref() {
    return href;
  }

  @Nullable
  @Override
  public String getTitle() {
    return title;
  }

  @Nullable
  @Override
  public AnchorTarget getTarget() {
    return target;
  }

  @Nullable
  @Override
  public String getRel() {
    return rel;
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return ariaLabel;
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return ariaDescribedBy;
  }

  @Nullable
  @Override
  public String getLang() {
    return lang;
  }

}
