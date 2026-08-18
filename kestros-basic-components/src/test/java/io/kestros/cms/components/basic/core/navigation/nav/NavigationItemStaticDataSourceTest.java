package io.kestros.cms.components.basic.core.navigation.nav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.kestros.cms.components.basic.api.navigation.KestrosNavigationItem;
import io.kestros.cms.components.basic.core.BaseDataSourceTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.junit.Test;

public class NavigationItemStaticDataSourceTest extends BaseDataSourceTest {

  private NavigationItemStaticDataSource dataSource;

  @Override
  public void doComponentSetup() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put("text", "Products");
    properties.put("href", "/content/products");
    final Resource resource =
        context.create().resource("/content/page/jcr:content/nav-item", properties);
    context.request().setResource(resource);
    dataSource = context.request().adaptTo(NavigationItemStaticDataSource.class);
  }

  @Override
  public void testToSyntheticResource() {
    assertEquals(KestrosNavigationItem.RESOURCE_TYPE,
        dataSource.toSyntheticResource(context.resourceResolver(), "/content/page/jcr:content")
            .getResourceType());
  }

  /**
   * isActive returned null from a method the interface declares @Nonnull, so a Java caller writing
   * {@code if (item.isActive())} unboxed a null and threw. It has to be a real Boolean now.
   *
   * <p>Nothing computes active state yet, so FALSE is the only correct answer; this asserts the
   * contract, not the feature.
   */
  @Test
  public void testIsActiveIsNeverNull() {
    assertFalse(dataSource.isActive());
  }

  @Test
  public void testGetNavigationItemsIsEmpty() {
    assertTrue(dataSource.getNavigationItems().isEmpty());
  }

  @Test
  public void testGetText() {
    assertEquals("Products", dataSource.getText());
  }
}
