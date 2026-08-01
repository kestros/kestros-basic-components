package io.kestros.cms.components.basic.core.content.button;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class KestrosButtonImpl extends BaseSyntheticResource implements KestrosButton {

  private String text;
  private String href;
  private String title;
  private AnchorTarget target;
  private String rel;
  private String ariaLabel;
  private String ariaDescribedBy;
  private String lang;
  private boolean disabled;
  private String resourceName;

  public KestrosButtonImpl(String text, String href,
      String title,
      AnchorTarget target, String rel,
      String ariaLabel, String ariaDescribedBy,
      String lang, boolean disabled,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = text;
    this.href = href;
    this.title = title;
    this.target = target;
    this.rel = rel;
    this.ariaLabel = ariaLabel;
    this.ariaDescribedBy = ariaDescribedBy;
    this.lang = lang;
    this.disabled = disabled;
    this.resourceName = forcedResourceName;
  }

  public KestrosButtonImpl(@Nonnull Resource resource,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      @Nullable String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.text = resource.getValueMap().get("text", String.class);
    this.href = resource.getValueMap().get("href", String.class);
    this.title = resource.getValueMap().get("title", String.class);
    this.target = AnchorTarget.lookup(resource);
    this.rel = resource.getValueMap().get("rel", String.class);
    this.ariaLabel = resource.getValueMap().get("ariaLabel", String.class);
    this.ariaDescribedBy = resource.getValueMap().get("ariaDescribedBy", String.class);
    this.lang = resource.getValueMap().get("lang", String.class);
    this.disabled = resource.getValueMap().get("disabled", false);
    if (StringUtils.isEmpty(href)) {
      throw new ComponentConfigurationException("Missing required property");
    }
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

  @Nonnull
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

  @Override
  public boolean isDisabled() {
    return disabled;
  }


}
