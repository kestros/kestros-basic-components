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

package io.kestros.cms.components.basic.testing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.services.ThemeProviderService;
import io.kestros.cms.uiframeworks.api.models.Theme;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.cms.uiframeworks.api.services.ThemeRetrievalService;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;

/**
 * Base test class for Kestros datasource models. Registers the services a container datasource
 * resolves while it builds its elements (component variations, UI-framework views, and the theme),
 * so a concrete datasource test only has to register its own model and build the resource to adapt.
 *
 * <p>Extend this, then in your own {@code @Before} call
 * {@code context.addModelsForClasses(YourDataSource.class)} and create the resource the datasource
 * adapts from.</p>
 */
@SuppressFBWarnings(value = "PRMC_POSSIBLY_REDUNDANT_METHOD_CALLS",
    justification = "The repeated call the detector sees is Mockito's any() matcher, which has"
        + " to be invoked once per stubbing. Both lookups deliberately resolve to the same"
        + " theme.")
public abstract class BaseDataSourceTest {

  @Rule
  public final SlingContext context = new SlingContext();

  /**
   * Registers the datasource services every Kestros datasource model resolves at construction
   * time (theme, UI framework, variation and view retrieval).
   *
   * @throws Exception if service registration fails.
   */
  @Before
  public void registerDataSourceServices() throws Exception {
    context.registerService(ComponentVariationRetrievalService.class,
        mock(ComponentVariationRetrievalService.class));
    context.registerService(ComponentUiFrameworkViewRetrievalService.class,
        mock(ComponentUiFrameworkViewRetrievalService.class));
    context.registerService(ThemeRetrievalService.class, mock(ThemeRetrievalService.class));

    // Elements resolve the containing page's theme and UI Framework as they are constructed.
    Theme theme = mock(Theme.class);
    when(theme.getUiFramework()).thenReturn(mock(UiFramework.class));
    final ThemeProviderService themeProviderService = mock(ThemeProviderService.class);
    // Both lookups resolve to the same theme; stubbed from one mock rather than two calls that
    // read as if they could differ.
    when(themeProviderService.getThemeForPage(any())).thenReturn(theme);
    when(themeProviderService.getThemeForComponent(any())).thenReturn(theme);
    context.registerService(ThemeProviderService.class, themeProviderService);
  }
}
