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

package io.kestros.cms.components.basic.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.assets.core.services.AssetRetrievalServiceImpl;
import io.kestros.cms.components.basic.api.exceptions.ComponentConfigurationException;
import io.kestros.cms.componenttypes.api.services.ComponentTypeRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.sitebuilding.api.services.KestrosClassLoader;
import io.kestros.cms.sitebuilding.api.services.ThemeProviderService;
import io.kestros.cms.uiframeworks.api.models.Theme;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.cms.uiframeworks.api.services.ThemeRetrievalService;
import io.kestros.cms.uiframeworks.api.services.UiFrameworkRetrievalService;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public abstract class BaseSyntheticTest {
  @Rule
  public SlingContext context = new SlingContext();
  public AssetRetrievalService assetRetrievalService;
  public ComponentVariationRetrievalService componentVariationRetrievalService;
  public ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  public ComponentTypeRetrievalService componentTypeRetrievalService;
  public UiFrameworkRetrievalService uiFrameworkRetrievalService;
  public ThemeRetrievalService themeRetrievalService;
  public ThemeProviderService themeProviderService;
  public KestrosClassLoader kestrosClassLoader;
  public Theme theme;
  public UiFramework uiFramework;

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    componentUiFrameworkViewRetrievalService = mock(ComponentUiFrameworkViewRetrievalService.class);
    componentVariationRetrievalService = mock(ComponentVariationRetrievalService.class);
    kestrosClassLoader = mock(KestrosClassLoader.class);
    componentTypeRetrievalService = mock(ComponentTypeRetrievalService.class);
    uiFrameworkRetrievalService = mock(UiFrameworkRetrievalService.class);
    themeRetrievalService = mock(ThemeRetrievalService.class);
    themeProviderService = mock(ThemeProviderService.class);
    assetRetrievalService = new AssetRetrievalServiceImpl();

    context.registerService(ComponentUiFrameworkViewRetrievalService.class,
            componentUiFrameworkViewRetrievalService);
    context.registerService(ComponentVariationRetrievalService.class,
            componentVariationRetrievalService);
    context.registerService(ComponentTypeRetrievalService.class, componentTypeRetrievalService);
    context.registerService(KestrosClassLoader.class, kestrosClassLoader);
    context.registerService(UiFrameworkRetrievalService.class, uiFrameworkRetrievalService);
    context.registerService(ThemeRetrievalService.class, themeRetrievalService);
    context.registerService(ThemeProviderService.class, themeProviderService);
    context.registerInjectActivateService(assetRetrievalService);

    // set up request URI
    context.request().setPathInfo("/content/sites/test.html");

    // set up page
    Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    context.create().resource("/content/sites/test", pageProperties);
    Map<String, Object> jcrContentProperties = new HashMap<>();
    jcrContentProperties.put("kes:theme", "/etc/ui-frameworks/my-framework/themes/my-theme");
    context.create().resource("/content/sites/test/jcr:content", jcrContentProperties);

    theme = mock(Theme.class);
    uiFramework = mock(UiFramework.class);
    when(theme.getUiFramework()).thenReturn(uiFramework);

    when(themeProviderService.getThemeForPage(any())).thenReturn(theme);
    when(themeProviderService.getThemeForComponent(any())).thenReturn(theme);
    setupElement();
  }

  public abstract void setupElement() throws ComponentConfigurationException;

  public ResourceResolver getResourceResolver() {
    return context.resourceResolver();
  }

  public UiFramework getUiFramework() {
    return mock(UiFramework.class);
  }

  @Test
  public void testToSynthetic() throws ComponentConfigurationException {
    testToSyntheticResource();
  }

  public abstract void testToSyntheticResource() throws ComponentConfigurationException;
}
