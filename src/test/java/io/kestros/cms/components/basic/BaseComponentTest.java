package io.kestros.cms.components.basic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import io.kestros.cms.componenttypes.core.models.ComponentVariationResource;
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
import javax.swing.text.ComponentView;
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

    setupViewsAndVariations();
    setupClassLoader();
    doComponentTypeSetup();
    doComponentSetup();
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
      pageJcrContentProperties.put("image", assetPath);
    }
    context.create().resource(path, pageProperties);
    context.create().resource(path + "/jcr:content", pageJcrContentProperties);

    context.create().resource(path + "/child-1", pageProperties);
    context.create().resource(path + "/child-1/jcr:content", pageJcrContentProperties);

    context.create().resource(path + "/child-2", pageProperties);
    context.create().resource(path + "/child-2/jcr:content", pageJcrContentProperties);

    context.create().resource(path + "/child-3", pageProperties);
    context.create().resource(path + "/child-3/jcr:content", pageJcrContentProperties);
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
  public abstract void testToSyntheticResource();


  public void setupClassLoader() {
    // do nothing, override in subclasses as needed
  }

  public void doComponentTypeSetup() throws ComponentTypeRetrievalException {
    // do nothing.
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
