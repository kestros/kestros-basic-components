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

package io.kestros.cms.components.basic.core.navigation.topnav;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.AnchorTarget;
import io.kestros.cms.components.basic.api.content.KestrosLink;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.api.navigation.KestrosTopNavigationItem;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Programmatic {@link KestrosTopNavigationItem}, built in code by a datasource rather than adapted
 * from an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosTopNavigationItemImpl extends BaseContainerSyntheticResource
    implements KestrosTopNavigationItem {
  private KestrosLink link;
  private List<KestrosTopNavigationItem> childItems;

  /**
   * Constructs a top navigation item impl.
   *
   * @param link Link.
   * @param childItems Child items.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosTopNavigationItemImpl(
      @Nonnull KestrosLink link,
      @Nonnull List<KestrosTopNavigationItem> childItems,
      @Nonnull BaseSlingModelDataSource dataSource,
      @Nonnull String resourcePrefix, String forcedResourceName)
      throws ComponentConfigurationException {
    super(dataSource, resourcePrefix, forcedResourceName);
    this.link = link;
    this.childItems = new ArrayList<>(childItems);
  }

  @Nonnull
  @Override
  public List<KestrosTopNavigationItem> getNavigationItems() {
    return new ArrayList<>(childItems);
  }

  @Nullable
  @Override
  public String getText() {
    return link.getText();
  }

  @Nullable
  @Override
  public String getHref() {
    return link.getHref();
  }

  @Nonnull
  @Override
  public AnchorTarget getTarget() {
    return link.getTarget();
  }

  @Nullable
  @Override
  public String getAriaLabel() {
    return link.getAriaLabel();
  }

  @Nullable
  @Override
  public String getTitle() {
    return link.getTitle();
  }

  @Nullable
  @Override
  public String getRel() {
    return link.getRel();
  }

  @Nullable
  @Override
  public String getAriaDescribedBy() {
    return link.getAriaDescribedBy();
  }

  @Nullable
  @Override
  public String getLang() {
    return link.getLang();
  }
}
