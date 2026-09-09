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

package io.kestros.cms.components.basic;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.assets.api.services.AssetRetrievalService;
import io.kestros.cms.assets.core.services.AssetRetrievalServiceImpl;
import io.kestros.cms.componenttypes.api.exceptions.ComponentTypeRetrievalException;
import io.kestros.cms.componenttypes.api.exceptions.ComponentVariationRetrievalException;
import io.kestros.cms.componenttypes.api.exceptions.ComponentViewRetrievalException;
import io.kestros.cms.componenttypes.api.exceptions.InvalidComponentUiFrameworkViewException;
import io.kestros.cms.componenttypes.api.models.ComponentUiFrameworkView;
import io.kestros.cms.componenttypes.api.models.ComponentVariation;
import io.kestros.cms.componenttypes.api.services.ComponentTypeRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentUiFrameworkViewRetrievalService;
import io.kestros.cms.componenttypes.api.services.ComponentVariationRetrievalService;
import io.kestros.cms.componenttypes.core.services.ComponentTypeRetrievalServiceImpl;
import io.kestros.cms.componenttypes.core.services.ComponentVariationRetrievalServiceImpl;
import io.kestros.cms.sitebuilding.api.services.KestrosClassLoader;
import io.kestros.cms.sitebuilding.api.services.ThemeProviderService;
import io.kestros.cms.uiframeworks.api.exceptions.UiFrameworkRetrievalException;
import io.kestros.cms.uiframeworks.api.models.Theme;
import io.kestros.cms.uiframeworks.api.models.UiFramework;
import io.kestros.cms.uiframeworks.api.services.ThemeRetrievalService;
import io.kestros.cms.uiframeworks.api.services.UiFrameworkRetrievalService;
import io.kestros.commons.structuredslingmodels.exceptions.ResourceNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public abstract class BaseComponentTest {

  @Rule
  public SlingContext context = new SlingContext();

  protected ComponentVariationRetrievalService componentVariationRetrievalService;
  protected ComponentUiFrameworkViewRetrievalService componentUiFrameworkViewRetrievalService;
  protected ComponentTypeRetrievalService componentTypeRetrievalService;
  protected UiFrameworkRetrievalService uiFrameworkRetrievalService;
  protected ThemeRetrievalService themeRetrievalService;
  protected ThemeProviderService themeProviderService;
  protected AssetRetrievalService assetRetrievalService;
  protected KestrosClassLoader kestrosClassLoader;
  protected Theme theme;
  protected UiFramework uiFramework;

  public Map<String, Map<String, String>> getDataSourceMap() {
    Map<String, Map<String, String>> dataSourceMap = new HashMap<>();

    Map<String, String> alertDataSources = new HashMap<>();
    alertDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.alert.AlertStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/alert", alertDataSources);

    Map<String, String> buttonDataSources = new HashMap<>();
    buttonDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.button.ButtonStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/button", buttonDataSources);

    // button group
    Map<String, String> buttonGroupDataSources = new HashMap<>();
    buttonGroupDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.buttongroup.ButtonGroupStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/button-group",
            buttonGroupDataSources);

    Map<String, String> cardDataSources = new HashMap<>();
    cardDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.card.CardStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/card", cardDataSources);
//
//    // code
//    Map<String, String> codeDataSources = new HashMap<>();
//    codeDataSources.put("default",
//        "io.kestros.cms.components.basic.core.content.code.CodeStaticDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/content/code", codeDataSources);
//
    // heading
    Map<String, String> headingDataSources = new HashMap<>();
    headingDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.heading.HeadingStaticDataSource");
    headingDataSources.put("page-title",
            "io.kestros.cms.components.basic.core.content.heading.HeadingPageTitleDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/heading", headingDataSources);
//
//     image
    Map<String, String> imageDataSources = new HashMap<>();
    imageDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.image.ImageStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/image", imageDataSources);
//
//    // richtext
//    Map<String, String> richTextDataSources = new HashMap<>();
//    richTextDataSources.put("default",
//        "io.kestros.cms.components.basic.core.content.richtext.RichTextStaticDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/content/rich-text", richTextDataSources);
//
    // video
    Map<String, String> videoDataSources = new HashMap<>();
    videoDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.video.VideoStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/video", videoDataSources);

    // video embed
    Map<String, String> videoEmbedDataSources = new HashMap<>();
    videoEmbedDataSources.put("default",
            "io.kestros.cms.components.basic.core.content.videoembed.VideoEmbedYouTubeDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/content/video-embed",
            videoEmbedDataSources);

    Map<String, String> cardListDataSources = new HashMap<>();
    cardListDataSources.put("default",
            "io.kestros.cms.components.basic.core.lists.cardlist.CardListStaticDataSource");
    cardListDataSources.put("child-pages",
            "io.kestros.cms.components.basic.core.lists.cardlist.CardListChildPagesDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/lists/card-list", cardListDataSources);
//
//    // link list
//    Map<String, String> linkListDataSources = new HashMap<>();
//    linkListDataSources.put("default",
//        "io.kestros.cms.components.basic.core.lists.linklist.LinkListStaticDataSource");
//    linkListDataSources.put("child-pages",
//        "io.kestros.cms.components.basic.core.lists.linklist.LinkListChildPagesDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/lists/link-list", linkListDataSources);
//
    // breadcrumbs

    Map<String, String> breadcrumbDataSources = new HashMap<>();
    breadcrumbDataSources.put("default",
            "io.kestros.cms.components.basic.core.navigation.breadcrumbs"
                    + ".BreadCrumbStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/navigation/breadcrumb",
            breadcrumbDataSources);

    Map<String, String> breadcrumbsDataSources = new HashMap<>();
    breadcrumbsDataSources.put("default",
            "io.kestros.cms.components.basic.core.navigation.breadcrumbs"
                    + ".BreadCrumbsPagePathDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/navigation/breadcrumbs",
            breadcrumbsDataSources);

//    // navigation
    Map<String, String> navigationDataSources = new HashMap<>();
    navigationDataSources.put("default",
            "io.kestros.cms.components.basic.core.navigation.nav.NavigationStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/navigation/navigation",
            navigationDataSources);

    Map<String, String> navigationItemDataSources = new HashMap<>();
    navigationItemDataSources.put("default",
        "io.kestros.cms.components.basic.core.navigation.nav.NavigationItemStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/navigation/navigation-item",
        navigationItemDataSources);

//    top-navigation
    Map<String, String> topNavigationDataSources = new HashMap<>();
    topNavigationDataSources.put("default",
            "io.kestros.cms.components.basic.core.navigation.topnav.TopNavigationStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/navigation/top-navigation",
            topNavigationDataSources);

    Map<String, String> topNavigationItemDataSources = new HashMap<>();
    topNavigationItemDataSources.put("default",
        "io.kestros.cms.components.basic.core.navigation.topnav.TopNavigationItemStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/navigation/top-navigation-item",
        topNavigationItemDataSources);
//
    // accordion
    Map<String, String> accordionPanelDataSources = new HashMap<>();
    accordionPanelDataSources.put("default",
            "io.kestros.cms.components.basic.core.structure.accordion"
                    + ".AccordionPanelStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/structure/accordion-panel",
            accordionPanelDataSources);

    Map<String, String> accordionDataSources = new HashMap<>();
    accordionDataSources.put("default",
            "io.kestros.cms.components.basic.core.structure.accordion.AccordionStaticDataSource");
    dataSourceMap.put("/libs/kestros/commons/components/structure/accordion",
            accordionDataSources);
//
//    // container
//    Map<String, String> containerDataSources = new HashMap<>();
//    containerDataSources.put("default",
//        "io.kestros.cms.components.basic.core.structure.ContainerStaticDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/structure/container",
//    containerDataSources);
//
//    // grid
//    Map<String, String> gridDataSources = new HashMap<>();
//    gridDataSources.put("default",
//        "io.kestros.cms.components.basic.core.structure.grid.GridStaticDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/structure/grid", gridDataSources);
//
//    // section
//    Map<String, String> sectionDataSources = new HashMap<>();
//    sectionDataSources.put("default",
//        "io.kestros.cms.components.basic.core.structure.section.SectionStaticDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/structure/section", sectionDataSources);
//
//    // tabs
//    Map<String, String> tabsDataSources = new HashMap<>();
//    tabsDataSources.put("default",
//        "io.kestros.cms.components.basic.core.structure.tabs.TabsStaticDataSource");
//    dataSourceMap.put("/libs/kestros/commons/components/structure/tabs", tabsDataSources);

    return dataSourceMap;
  }


  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    componentUiFrameworkViewRetrievalService = mock(ComponentUiFrameworkViewRetrievalService.class);
    componentVariationRetrievalService = spy(new ComponentVariationRetrievalServiceImpl());
    kestrosClassLoader = mock(KestrosClassLoader.class);
    componentTypeRetrievalService = new ComponentTypeRetrievalServiceImpl();
    uiFrameworkRetrievalService = mock(UiFrameworkRetrievalService.class);
    themeRetrievalService = mock(ThemeRetrievalService.class);
    themeProviderService = mock(ThemeProviderService.class);
    assetRetrievalService = new AssetRetrievalServiceImpl();

    context.registerService(ComponentUiFrameworkViewRetrievalService.class,
            componentUiFrameworkViewRetrievalService);
    context.registerInjectActivateService(componentVariationRetrievalService);
    context.registerInjectActivateService(componentTypeRetrievalService);
    context.registerService(KestrosClassLoader.class, kestrosClassLoader);
    context.registerService(UiFrameworkRetrievalService.class, uiFrameworkRetrievalService);
    context.registerService(ThemeRetrievalService.class, themeRetrievalService);
    context.registerService(ThemeProviderService.class, themeProviderService);

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

    setupViewsAndVariations();
    setupClassLoaderForAllComponentTypes();
    doComponentTypeSetup();
    doComponentSetup();
  }

  public void registerAssetRetrievalService() {
    context.registerInjectActivateService(assetRetrievalService);
  }

  public abstract void doComponentSetup() throws AssetCollectionRetrievalException;

  public void setupSamplePage(String path, String assetPath) {
    Map<String, Object> pageProperties = new HashMap<>();
    pageProperties.put("jcr:primaryType", "kes:Page");
    Map<String, Object> pageJcrContentProperties = new HashMap<>();
    pageJcrContentProperties.put("jcr:primaryType", "nt:unstructured");
    pageJcrContentProperties.put("jcr:title", "Title");
    pageJcrContentProperties.put("jcr:description", "Description");
    if (assetPath != null) {
      pageJcrContentProperties.put("cardImage", assetPath);
      pageJcrContentProperties.put("image", assetPath);
    }
    context.create().resource(path, pageProperties);
    context.create().resource(path + "/jcr:content", pageJcrContentProperties);

    context.create().resource(path + "/child-1", pageProperties);
    context.create().resource(path + "/child-1/jcr:content", pageJcrContentProperties);
    assertEquals("kes:Page", context.resourceResolver().getResource(path + "/child-1").getValueMap()
            .get("jcr:primaryType", String.class));

    context.create().resource(path + "/child-2", pageProperties);
    context.create().resource(path + "/child-2/jcr:content", pageJcrContentProperties);
    assertEquals("kes:Page", context.resourceResolver().getResource(path + "/child-2").getValueMap()
            .get("jcr:primaryType", String.class));

    context.create().resource(path + "/child-3", pageProperties);
    context.create().resource(path + "/child-3/jcr:content", pageJcrContentProperties);
    assertEquals("kes:Page", context.resourceResolver().getResource(path + "/child-3").getValueMap()
            .get("jcr:primaryType", String.class));
  }

  public void setUpSampleCollection(String path) throws AssetCollectionRetrievalException {
    Map<String, Object> collectionProperties = new HashMap<>();
    collectionProperties.put("jcr:primaryType", "kes:AssetCollection");
    context.create().resource(path, collectionProperties);
    Map<String, Object> assetProperties = new HashMap<>();
    Map<String, Object> assetJcrContentProperties = new HashMap<>();
    assetJcrContentProperties.put("jcr:primaryType", "kes:Asset");
    assetJcrContentProperties.put("jcr:title", "Asset 1 Title");
    assetJcrContentProperties.put("jcr:description", "Asset 1 Description");
    context.create().resource(path + "/asset-1", assetProperties);
    context.create().resource(path + "/asset-1/jcr:content", assetJcrContentProperties);
    assetJcrContentProperties.put("jcr:title", "Asset 2 Title");
    assetJcrContentProperties.put("jcr:description", "Asset 2 Description");
    context.create().resource(path + "/asset-2", assetProperties);
    context.create().resource(path + "/asset-2/jcr:content", assetJcrContentProperties);
    assetJcrContentProperties.put("jcr:title", "Asset 3 Title");
    assetJcrContentProperties.put("jcr:description", "Asset 3 Description");
    context.create().resource(path + "/asset-3", assetProperties);
    context.create().resource(path + "/asset-3/jcr:content", assetJcrContentProperties);
  }

  @Test
  public abstract void testToSyntheticResource() throws AssetCollectionRetrievalException;


  public void setupClassLoaderForAllComponentTypes() {
    // do nothing, override in subclasses as needed.
  }

  public void setupClassLoaderForComponentType(Map<String, String> componentDataSourceMap) {
    // do nothing, override in subclasses as needed
  }

  public void doComponentTypeSetup() throws ComponentTypeRetrievalException {
    for (Map.Entry<String, Map<String, String>> entry1 : getDataSourceMap().entrySet()) {
      Map<String, String> componentEntry = entry1.getValue();
      Map<String, Object> properties = new HashMap<>();
      properties.put("jcr:primaryType", "kes:ComponentType");
      String path = entry1.getKey();
      context.create().resource(path, properties);
      context.create().resource(path + "/datasources");
      for (Map.Entry<String, String> dataSourceEntry : componentEntry.entrySet()) {
        try {
          Map<String, Object> dsProperties = new HashMap<>();
          dsProperties.put("jcr:primaryType", "nt:unstructured");
          dsProperties.put("classPath", dataSourceEntry.getValue());
          context.create().resource(path + "/datasources/" + dataSourceEntry.getKey(),
                  dsProperties);
        } catch (Exception e) {
          // nothing.
        }
      }
    }

  }

  public void setupViewsAndVariations() throws InvalidComponentUiFrameworkViewException,
          UiFrameworkRetrievalException, ResourceNotFoundException,
          ComponentViewRetrievalException, ComponentVariationRetrievalException {
    ComponentUiFrameworkView view = mock(ComponentUiFrameworkView.class);
    when(componentUiFrameworkViewRetrievalService.getResolvedComponentUiFrameworkView(any(), any(),
            any())).thenReturn(view);

    Map<String, Object> variationProperties = new HashMap<>();
    variationProperties.put("jcr:primaryType", "kes:ComponentVariation");
    context.create().resource("/variations/variation1", variationProperties);
    context.create().resource("/variations/variation2", variationProperties);
    context.create().resource("/variations/variation3", variationProperties);


    ComponentVariation variation1
            = componentVariationRetrievalService.getComponentVariation(
            "/variations/variation1", context.resourceResolver());
    ComponentVariation variation2
            = componentVariationRetrievalService.getComponentVariation(
            "/variations/variation2", context.resourceResolver());
    ComponentVariation variation3
            = componentVariationRetrievalService.getComponentVariation(
            "/variations/variation3", context.resourceResolver());
    doReturn(Arrays.asList(variation1, variation2, variation3)).when(
            componentVariationRetrievalService).getComponentVariations(any());

  }


}
