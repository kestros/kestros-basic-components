package io.kestros.cms.components.basic.core.navigation.nav;

import static org.junit.Assert.assertEquals;

import io.kestros.cms.assets.api.exceptions.AssetCollectionRetrievalException;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

/**
 * KestrosNavigationItem.isActive() is declared @Nonnull and both implementations returned null
 * from it, so anything unboxing the result threw. The interface now carries the default the
 * sibling top-navigation interface already used.
 */
public class NavigationItemStaticDataSourceTest extends BaseDataSourceTest {

  private NavigationItemStaticDataSource navigationItem;
  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();

  @Override
  public void doComponentSetup() throws AssetCollectionRetrievalException {
  }

  @Override
  public void testToSyntheticResource() throws AssetCollectionRetrievalException {
  }

  @Test
  public void testIsActiveWhenTheHrefIsNotTheRequestedPage() {
    context.request().setPathInfo("/content/sites/test.html");
    properties.put("href", "/content/sites/other.html");
    resource = context.create().resource("/content/nav/item-inactive", properties);
    context.request().setResource(resource);
    navigationItem = context.request().adaptTo(NavigationItemStaticDataSource.class);

    assertEquals(Boolean.FALSE, navigationItem.isActive());
  }

  @Test
  public void testIsActiveWhenThereIsNoHref() {
    context.request().setPathInfo("/content/sites/test.html");
    resource = context.create().resource("/content/nav/item-no-href", properties);
    context.request().setResource(resource);
    navigationItem = context.request().adaptTo(NavigationItemStaticDataSource.class);

    assertEquals(Boolean.FALSE, navigationItem.isActive());
  }
}
