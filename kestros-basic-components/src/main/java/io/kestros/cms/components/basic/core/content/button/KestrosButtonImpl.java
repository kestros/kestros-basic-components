/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.components.basic.core.content.button;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

/**
 * Programmatic {@link KestrosButton}, built in code by a datasource rather than adapted from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
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

  /**
   * Constructs a button impl.
   *
   * @param text Text.
   * @param href Href.
   * @param title Title.
   * @param target Target.
   * @param rel Rel.
   * @param ariaLabel Aria label.
   * @param ariaDescribedBy Aria described by.
   * @param lang Lang.
   * @param disabled Disabled.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
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

  /**
   * Constructs a button impl.
   *
   * @param resource Resource.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
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
    this.disabled = resource.getValueMap().get("disabled", Boolean.FALSE);
    if (StringUtils.isEmpty(href)) {
      throw new ComponentConfigurationException(String.format(
          "Unable to build a button at %s: href is required and was empty.",
          resource.getPath()));
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
