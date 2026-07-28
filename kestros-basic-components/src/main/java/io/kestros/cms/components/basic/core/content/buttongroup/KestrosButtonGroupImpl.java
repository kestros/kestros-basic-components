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

package io.kestros.cms.components.basic.core.content.buttongroup;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.components.basic.api.content.KestrosButton;
import io.kestros.cms.components.basic.api.content.KestrosButtonGroup;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.components.basic.core.BaseContainerSyntheticResource;
import io.kestros.cms.components.basic.core.BaseSlingModelDataSource;
import io.kestros.cms.components.basic.core.content.button.KestrosButtonImpl;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.sling.api.resource.Resource;

/**
 * Programmatic {@link KestrosButtonGroup}, built in code by a datasource rather than adapted from
 * an
 * authored resource.
 */
@SuppressFBWarnings("IMC_IMMATURE_CLASS_NO_TOSTRING")
public class KestrosButtonGroupImpl extends BaseContainerSyntheticResource implements
                                                                           KestrosButtonGroup {

  private List<KestrosButton> buttons;
  private List<ComponentVariation> buttonVariations;

  /**
   * Constructs a button group impl.
   *
   * @param buttons Buttons.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosButtonGroupImpl(@Nonnull final List<KestrosButton> buttons,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.buttons = new ArrayList<>(buttons);
    this.buttonVariations = dataSource.getElementVariations("button", KestrosButton.RESOURCE_TYPE);
  }

  /**
   * Constructs a button group impl.
   *
   * @param buttonResource Button resource.
   * @param dataSource Data source.
   * @param resourcePrefix Resource prefix.
   * @param forcedResourceName Forced resource name.
   * @throws ComponentConfigurationException If the component configuration is not valid.
   */
  public KestrosButtonGroupImpl(@Nonnull Resource buttonResource,
      @Nonnull BaseSlingModelDataSource dataSource,
      String resourcePrefix,
      String forcedResourceName) throws
      ComponentConfigurationException {
    super(dataSource, resourcePrefix,
        forcedResourceName);
    this.buttons = new ArrayList<>();
    this.buttonVariations = dataSource.getElementVariations("button", KestrosButton.RESOURCE_TYPE);
    List<KestrosButton> buttons = new ArrayList<>();
    for (Resource childResource : buttonResource.getChildren()) {
      buttons.add(new KestrosButtonImpl(childResource, dataSource,
          "button",
          childResource.getName()));
    }
    if (buttonResource.getValueMap().get("href", String.class) != null) {
      //In case of single button defined as button group.
      buttons.add(new KestrosButtonImpl(buttonResource, dataSource,
          "button",
          "buttonElement"));
    }
    this.buttons = new ArrayList<>(buttons);
    if (this.buttons.isEmpty()) {
      throw new ComponentConfigurationException(String.format(
          "Unable to build the button group %s: a button group must have at least one button.",
          String.valueOf(resourcePrefix)));
    }
  }

  @Nonnull
  @Override
  public List<KestrosButton> getButtonsElements() {
    return new ArrayList<>(buttons);
  }

  @Nonnull
  @Override
  public List<ComponentVariation> getButtonVariations() {
    return new ArrayList<>(buttonVariations);
  }
}
