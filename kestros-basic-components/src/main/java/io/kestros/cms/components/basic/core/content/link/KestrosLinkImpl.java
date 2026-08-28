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

package io.kestros.cms.components.basic.core.content.link;

import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.BaseSyntheticResource;
import io.kestros.cms.components.basic.core.LinkUtils;
import io.kestros.cms.sitebuilding.api.models.BaseContentPage;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of the link element.
 */
public class KestrosLinkImpl extends BaseSyntheticResource implements KestrosLink {

  private String text;
  private String href;
  private String title;
  private AnchorTarget target;
  private String rel;
  private String ariaLabel;
  private String ariaDescribedBy;
  private String lang;


  /**
   * Builds a link pointing at the given page.
   *
   * @param page Page the link points at.
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
   */
  public KestrosLinkImpl(@Nonnull BaseContentPage page,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.text = page.getDisplayTitle();
    this.href = LinkUtils.getLink(page.getPath());
    this.title = title;
    this.target = target;
    this.rel = rel;
    this.ariaLabel = ariaLabel;
    this.ariaDescribedBy = ariaDescribedBy;
    this.lang = lang;
  }


  /**
   * Builds a link from the values it is given.
   *
   * @param text Visible link text.
   * @param href Destination the link points at.
   * @param title Title attribute for the link.
   * @param target Target attribute for the link.
   * @param rel Relationship attribute for the link.
   * @param ariaLabel ARIA label for the link.
   * @param ariaDescribedBy ARIA described-by ID reference.
   * @param lang Language of the link text.
   * @param dataSource Datasource the element is built by.
   * @param resourcePrefix Prefix for the path of the synthetic resource.
   * @param forcedResourceName Name to force on the synthetic resource.
   * @throws ComponentConfigurationException The element could not be configured.
   */
  public KestrosLinkImpl(String text, String href, String title, AnchorTarget target, String rel,
      String ariaLabel, String ariaDescribedBy, String lang,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
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
